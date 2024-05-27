package ru.katiafill.bookings.flight.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {
    private String code;
    private LocalizedString model;
    private Integer range;
}
