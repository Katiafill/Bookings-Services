package ru.katiafill.bookings.flight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.katiafill.bookings.shared.exception.ExceptionController;
import ru.katiafill.bookings.shared.exception.ResponseError;

import java.util.Map;

@ControllerAdvice
public class FightExceptionController extends ExceptionController {

    @ExceptionHandler(InvalidFlightRequestException.class)
    public ResponseEntity<ResponseError> handleFlightException(InvalidFlightRequestException ex) {
        ResponseError error =  new ResponseError(ex.getLocalizedMessage());
        if (ex.getCause() != null) {
            error.setErrors(Map.of("details", ex.getCause().getLocalizedMessage()));
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
