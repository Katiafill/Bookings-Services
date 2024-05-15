package ru.katiafill.bookings.aircraft.dto;

import lombok.Getter;
import lombok.Setter;
import ru.katiafill.bookings.aircraft.model.FareConditions;

@Getter
@Setter
public class SeatDTO {
    private String seatNo;
    private FareConditions fareConditions;
}
