package ru.katiafill.bookings.flight.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 6)
    @Column(name = "flight_no", length = 6, nullable = false)
    private String flightNo;

    @NotNull
    @Column(name = "scheduled_departure", nullable = false)
    private ZonedDateTime scheduledDeparture;

    @NotNull
    @Column(name = "scheduled_arrival", nullable = false)
    private ZonedDateTime scheduledArrival;

    @Column(name = "actual_departure")
    private ZonedDateTime actualDeparture;

    @Column(name = "actual_arrival")
    private ZonedDateTime actualArrival;

    @NotNull
    @Column(name = "status", nullable = false)
    private FlightStatus status;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "aircraft_code", length = 3, nullable = false)
    private String aircraftCode;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "departure_airport", length = 3, nullable = false)
    private String departureAirportCode;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "arrival_airport", length = 3, nullable = false)
    private String arrivalAirportCode;
}
