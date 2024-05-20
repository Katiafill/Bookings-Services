package ru.katiafill.bookings.airport.exception;

/* Ошибка отсутсвия данных в БД. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
