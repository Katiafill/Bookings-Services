package ru.katiafill.bookings.aircraft.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;
import ru.katiafill.bookings.aircraft.service.SeatService;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft/{id}/seats")
@AllArgsConstructor
public class AircraftSeatsController {

    private final SeatService service;

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping
    public List<Seat> getSeatsByAircraft(@PathVariable String id,
                                         @RequestParam(required = false) FareConditions conditions) {
        if (conditions == null) {
            return service.getAllSeats(id);
        } else {
            return service.getSeatsByFareConditions(id, conditions);
        }
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/{seatNo}")
    public Seat getSeat(@PathVariable String id, String seatNo) {
        return service.getSeat(id, seatNo);
    }

    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping
    public List<Seat> addSeats(@PathVariable String id, @RequestBody List<Seat> seats) {
        return service.addSeats(seats, id);
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping
    public List<Seat> updateSeats(@PathVariable String id, @RequestBody List<Seat> seats) {
        return service.updateSeats(seats, id);
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping
    public void deleteSeats(@PathVariable String id, @RequestBody List<Seat> seats) {
        service.deleteSeats(seats, id);
    }
}
