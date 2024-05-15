package ru.katiafill.bookings.aircraft.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;

import java.util.List;

@Repository
public interface SeatRepository extends ListCrudRepository<Seat, Seat.SeatPK> {
    List<Seat> findAllByAircraftCode(String aircraftCode);
    List<Seat> findAllByAircraftCodeAndFareConditions(String aircraftCode, FareConditions conditions);
}
