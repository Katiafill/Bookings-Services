package ru.katiafill.bookings.aircraft.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.aircraft.service.AircraftService;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@AllArgsConstructor
public class AircraftController {
    private final AircraftService service;

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping
    public List<Aircraft> getAircrafts() {
        return service.getAircrafts();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/{id}")
    public Aircraft getAircraft(@PathVariable String id, @RequestParam(defaultValue = "false") boolean full) {
        return service.getAircraft(id, full);
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public Aircraft addAircraft(@RequestBody Aircraft aircraft) {
        return service.createAircraft(aircraft);
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping
    public Aircraft updateAircraft(@RequestBody Aircraft aircraft) {
        return service.updateAircraft(aircraft);
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable String id) {
        service.deleteAircraft(id);
    }

}
