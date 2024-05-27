package ru.katiafill.bookings.flight.model;

public enum FlightStatus {
    ON_TIME("On Time"),
    DELAYED("Delayed"),
    DEPARTED("Departed"),
    ARRIVED("Arrived"),
    SCHEDULED("Scheduled"),
    CANCELLED("Cancelled");

    private final String name;

    FlightStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
