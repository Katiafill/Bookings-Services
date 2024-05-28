package ru.katiafill.bookings.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handlerException(Exception ex) {
        return new ResponseEntity<>(new ResponseError(ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Отлавливает ошибки вида ResourceNotFoundException и упаковывает в ResponseEntity с httpStatus = 404.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ResponseError(ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    // Отлавливает ошибки вида ResourceAlreadyExistsException и упаковывает в ResponseEntity с httpStatus = 400.
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseError> handlerResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
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
