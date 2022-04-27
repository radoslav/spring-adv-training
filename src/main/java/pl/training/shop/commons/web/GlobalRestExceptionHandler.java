package pl.training.shop.commons.web;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalRestExceptionHandler extends RestExceptionHandler {

    public GlobalRestExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> onException(Exception exception, Locale locale) {
        return createResponse(exception, INTERNAL_SERVER_ERROR, locale);
    }

}
