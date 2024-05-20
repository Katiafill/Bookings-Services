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
    @NotNull
    @Size(min = 3, max = 3, message = "Airport code must be 3 length characters.")
    private String code;

    @Column(name = "airport_name", nullable = false)
    @Type(JsonBinaryType.class)
    @NotNull
    @Valid
    private LocalizedString name;

    @Column(nullable = false)
    @Type(JsonBinaryType.class)
    @NotNull
    @Valid
    private LocalizedString city;

    @Column(nullable = false)
    @Type(PointType.class)
    @NotNull
    private Point coordinates;

    @Column(nullable = false)
    @NotBlank
    private String timezone;
}
