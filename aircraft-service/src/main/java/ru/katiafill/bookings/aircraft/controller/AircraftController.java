package ru.katiafill.bookings.aircraft.controller;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.katiafill.bookings.aircraft.dto.AircraftDTO;
import ru.katiafill.bookings.aircraft.dto.SeatDTO;
import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;
import ru.katiafill.bookings.aircraft.service.AircraftService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aircraft")
@AllArgsConstructor
public class AircraftController {
    private final AircraftService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<AircraftDTO> getAircrafts() {
        return service.getAircrafts()
                .stream()
                .map(a -> modelMapper.map(a, AircraftDTO.class)
                ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AircraftDTO getAircraftById(@PathVariable String id, @RequestParam(defaultValue = "false") boolean full) {
        Aircraft aircraft = service.getAircraft(id);

        AircraftDTO dto = modelMapper.map(aircraft, AircraftDTO.class);

//        if (full) {
//            List<Seat> seats = service.getAllSeats(id);
//            dto.setSeats(seats.stream().map(s -> modelMapper.map(s, SeatDTO.class)).collect(Collectors.toList()));
//        }

        return dto;
    }

    @PostMapping
    public void addAircraft(@RequestBody AircraftDTO aircraft) {
        Aircraft mAircraft = modelMapper.map(aircraft, Aircraft.class);

        service.createAircraft(mAircraft);
    }

    @PutMapping
    public void updateAircraft(@RequestBody AircraftDTO aircraftDTO) {
        service.updateAircraft(modelMapper.map(aircraftDTO, Aircraft.class));
    }

    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable String id) {
        service.deleteAircraft(id);
    }

}
