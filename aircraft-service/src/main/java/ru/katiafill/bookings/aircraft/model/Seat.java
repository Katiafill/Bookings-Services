package ru.katiafill.bookings.aircraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Seat.SeatPK.class)
public class Seat {
    @Id
    @Column(name = "aircraft_code", length = 3, nullable = false)
    @JsonIgnore
    private String aircraftCode;

    @Id
    @Column(name = "seat_no", length = 4, nullable = false)
    private String seatNo;

    @Column(name = "fare_conditions", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private FareConditions fareConditions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatPK implements Serializable {
        private String aircraftCode;
        private String seatNo;
    }
}