package pl.training.shop.payments.adapters.rest;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.training.shop.commons.web.ExceptionDto;
import pl.training.shop.commons.web.RestExceptionHandler;
import pl.training.shop.payments.domain.PaymentNotFoundException;

import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice("pl.training.shop.payments.adapters.rest")
public class PaymentRestExceptionHandler extends RestExceptionHandler {

    public PaymentRestExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionDto> onPaymentNotFound(PaymentNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

}
