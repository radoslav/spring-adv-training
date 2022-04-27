package pl.training.shop.commons.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSource messageSource;

    protected ResponseEntity<ExceptionDto> createResponse(Exception exception, HttpStatus status, Locale locale) {
        var description = getDescription(exception.getClass().getSimpleName(), locale);
        return ResponseEntity.status(status).body(new ExceptionDto(description));
    }

    protected String getDescription(String key, Locale locale) {
        String description;
        try {
            description = messageSource.getMessage(key, new Object[] {}, locale);
        } catch (NoSuchMessageException exception) {
            description = key;
        }
        return description;
    }

}
