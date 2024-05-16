package ru.katiafill.bookings.aircraft.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Data
@Entity
@Table(name = "aircrafts_data")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {
    // Aircraft code, IATA
    @Id
    @Column(name = "aircraft_code", length = 3, nullable = false, unique = true)
    private String code;

    // Aircraft model
    @Column(nullable = false, columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private LocalizedString model;

    // Maximal flying distance, km
    @Column(nullable = false)
    private Integer range;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Seat> seats;
}
