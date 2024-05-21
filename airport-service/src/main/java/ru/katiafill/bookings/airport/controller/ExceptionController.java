package ru.katiafill.bookings.airport.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.katiafill.bookings.airport.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.airport.exception.ResourceNotFoundException;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @Autowired
    private MessageSource messageSource;

    @Data
    public static class ResponseError {
        private String message;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private Map<String, String> errors;

        public ResponseError(String message, Map<String, String> errors) {
            this.message = message;
            this.errors = errors;
        }

        public ResponseError(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handlerException(Exception ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(new ResponseError(ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Отлавливает ошибки вида ResourceNotFoundException и упаковывает в ResponseEntity с httpStatus = 404.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        //log.error(ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(new ResponseError(ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    // Отлавливает ошибки вида ResourceNotFoundException и упаковывает в ResponseEntity с httpStatus = 404.
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseError> handlerResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        //log.error(ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(new ResponseError(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        e -> ((FieldError)e).getField(),
                        e -> e.getDefaultMessage()));
        return new ResponseEntity<>(new ResponseError(
                messageSource.getMessage("validation.error", null, Locale.getDefault()), errors),
                HttpStatus.BAD_REQUEST);
    }

}
