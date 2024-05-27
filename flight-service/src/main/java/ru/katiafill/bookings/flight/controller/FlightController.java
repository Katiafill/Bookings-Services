package ru.katiafill.bookings.flight.controller;

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

    @GetMapping("/flightNo/{flightNo}")
    public List<Flight> getFlights(@PathVariable String flightNo) {
        return service.getFlightsByFlightNo(flightNo);
    }

    @GetMapping("/status/{status}")
    public List<Flight> getFlights(@PathVariable FlightStatus status) {
        return service.getFlightsByStatus(status);
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return service.getFlight(id);
    }

    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return service.createFlight(flight);
    }

    @PutMapping
    public Flight updateFlight(@RequestBody Flight flight) {
        return service.updateFlight(flight);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        service.deleteFlight(id);
    }
}
