package ru.katiafill.bookings.airport.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.katiafill.bookings.airport.model.Airport;
import ru.katiafill.bookings.airport.service.AirportService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/airport")
public class AirportController {

    private AirportService service;

    @GetMapping
    public List<Airport> getAirports() {
        return service.getAirports();
    }

    @GetMapping("/{id}")
    public Airport getAirport(@PathVariable String id) {
        return service.getAirport(id);
    }

    @PostMapping
    public Airport createAirport(@Valid @RequestBody Airport airport) {
        return service.createAirport(airport);
    }

    @PutMapping
    public Airport updateAirport(@Valid @RequestBody Airport airport) {
        return service.updateAirport(airport);
    }

    @DeleteMapping("/{id}")
    public void deleteAirport(@PathVariable String id) {
        service.deleteAirport(id);
    }

}
