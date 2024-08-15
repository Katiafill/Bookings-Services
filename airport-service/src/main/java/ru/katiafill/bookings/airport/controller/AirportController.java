package ru.katiafill.bookings.airport.controller;

import jakarta.annotation.security.RolesAllowed;
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

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping
    public List<Airport> getAirports() {
        return service.getAirports();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/{id}")
    public Airport getAirport(@PathVariable String id) {
        return service.getAirport(id);
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public Airport createAirport(@Valid @RequestBody Airport airport) {
        return service.createAirport(airport);
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping
    public Airport updateAirport(@Valid @RequestBody Airport airport) {
        return service.updateAirport(airport);
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteAirport(@PathVariable String id) {
        service.deleteAirport(id);
    }

}
