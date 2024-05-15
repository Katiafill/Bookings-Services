package ru.katiafill.bookings.aircraft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedString {
    private String en;
    private String ru;
}