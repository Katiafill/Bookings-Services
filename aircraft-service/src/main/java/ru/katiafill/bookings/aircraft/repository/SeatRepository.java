package ru.katiafill.bookings.aircraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Seat.SeatPK> {
    List<Seat> findAllByAircraftCode(String aircraftCode);
    List<Seat> findAllByAircraftCodeAndFareConditions(String aircraftCode, FareConditions conditions);
}
