package ru.katiafill.bookings.airport.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.katiafill.bookings.airport.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.airport.exception.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Data
    @AllArgsConstructor
    public static class ResponseError {
        private String message;
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

}
