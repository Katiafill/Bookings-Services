package ru.katiafill.bookings.flight.exception;

public class InvalidFlightRequestException extends RuntimeException {
    public InvalidFlightRequestException(String message, Throwable ex) {
        super(message, ex);
    }

    public InvalidFlightRequestException(String message) {
        super(message);
    }
}
