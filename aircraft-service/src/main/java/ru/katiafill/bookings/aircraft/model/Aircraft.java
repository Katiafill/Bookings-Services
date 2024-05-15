package ru.katiafill.bookings.aircraft.model;


import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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
    @Column(nullable = false, columnDefinition = "jsonb", unique = true)
    @Type(JsonBinaryType.class)
    private LocalizedString model;

    // Maximal flying distance, km
    @Column(nullable = false)
    private Integer range;
}
