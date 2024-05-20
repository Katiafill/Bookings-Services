package ru.katiafill.bookings.airport.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "airports_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    @Id
    @Column(name = "airport_code", length = 3, nullable = false)
    private String code;

    @Column(name = "airport_name", nullable = false)
    @Type(JsonBinaryType.class)
    private LocalizedString name;

    @Column(nullable = false)
    @Type(JsonBinaryType.class)
    private LocalizedString city;

    @Column(nullable = false)
    @Type(PointType.class)
    private Point coordinates;

    @Column(nullable = false)
    private String timezone;
}
