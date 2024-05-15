package ru.katiafill.bookings.aircraft.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.katiafill.bookings.aircraft.dto.SeatDTO;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.service.SeatService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aircraft/{id}/seats")
@AllArgsConstructor
public class AircraftSeatsController {

    private final SeatService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<SeatDTO> getSeatsByAircraft(@PathVariable String id,
                                            @RequestParam(required = false) FareConditions conditions) {
        if (conditions == null) {
            return service.getAllSeats(id)
                    .stream()
                    .map(s -> modelMapper.map(s, SeatDTO.class))
                    .collect(Collectors.toList());
        } else {
            return service.getSeatsByFareConditions(id, conditions)
                    .stream()
                    .map(s -> modelMapper.map(s, SeatDTO.class))
                    .collect(Collectors.toList());
        }
    }
}
