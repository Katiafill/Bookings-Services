package ru.katiafill.bookings.shared.exception;

/* Ошибка отсутсвия данных в БД. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
