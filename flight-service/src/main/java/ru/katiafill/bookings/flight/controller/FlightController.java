package ru.katiafill.bookings.flight.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.katiafill.bookings.flight.model.Flight;
import ru.katiafill.bookings.flight.model.FlightStatus;
import ru.katiafill.bookings.flight.service.FlightService;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@AllArgsConstructor
public class FlightController {
    private final FlightService service;

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/flightNo/{flightNo}")
    public List<Flight> getFlights(@PathVariable String flightNo) {
        return service.getFlightsByFlightNo(flightNo);
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/status/{status}")
    public List<Flight> getFlights(@PathVariable FlightStatus status) {
        return service.getFlightsByStatus(status);
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return service.getFlight(id);
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public Flight createFlight(@Valid @RequestBody Flight flight) {
        return service.createFlight(flight);
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping
    public Flight updateFlight(@Valid @RequestBody Flight flight) {
        return service.updateFlight(flight);
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        service.deleteFlight(id);
    }
}
