package ru.katiafill.bookings.airport.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @NotNull(message = "{airport.errors.code.notnull}")
    @Size(min = 3, max = 3, message = "{airport.errors.code.size}")
    private String code;

    @Column(name = "airport_name", nullable = false)
    @Type(JsonBinaryType.class)
    @NotNull(message = "{airport.errors.name.notnull}")
    @Valid
    private LocalizedString name;

    @Column(nullable = false)
    @Type(JsonBinaryType.class)
    @NotNull(message = "{airport.errors.city.notnull}")
    @Valid
    private LocalizedString city;

    @Column(nullable = false)
    @Type(PointType.class)
    @NotNull(message = "{airport.errors.coordinates.notnull}")
    private Point coordinates;

    @Column(nullable = false)
    @NotBlank(message = "{airport.errors.timezone.notnull}")
    private String timezone;
}
