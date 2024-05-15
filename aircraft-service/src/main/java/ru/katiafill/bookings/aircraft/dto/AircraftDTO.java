package ru.katiafill.bookings.aircraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.katiafill.bookings.aircraft.model.LocalizedString;

import java.util.List;

@Getter
@Setter
public class AircraftDTO {
    private String code;
    private LocalizedString model;
    private Integer range;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SeatDTO> seats;
}
