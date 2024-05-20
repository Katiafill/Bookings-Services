package ru.katiafill.bookings.airport.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedString {
    @NotBlank
    private String en;
    @NotBlank
    private String ru;
}