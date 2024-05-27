package ru.katiafill.bookings.flight.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false)
    private Long id;

    @Column(name = "flight_no", length = 6, nullable = false)
    private String flightNo;

    @Column(name = "scheduled_departure", nullable = false)
    private ZonedDateTime scheduledDeparture;

    @Column(name = "scheduled_arrival", nullable = false)
    private ZonedDateTime scheduledArrival;

    @Column(name = "actual_departure")
    private ZonedDateTime actualDeparture;

    @Column(name = "actual_arrival")
    private ZonedDateTime actualArrival;

    @Column(name = "status", nullable = false)
    private FlightStatus status;

    @Column(name = "aircraft_code", nullable = false)
    private String aircraftCode;

    @Column(name = "departure_airport", nullable = false)
    private String departureAirportCode;

    @Column(name = "arrival_airport",  nullable = false)
    private String arrivalAirportCode;
}
