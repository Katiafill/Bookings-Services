package ru.katiafill.bookings.flight.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    private String code;
    private LocalizedString name;
    private LocalizedString city;
    private Point coordinates;
    private String timezone;
}
