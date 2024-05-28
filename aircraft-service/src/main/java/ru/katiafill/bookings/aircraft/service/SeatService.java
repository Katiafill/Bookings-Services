package ru.katiafill.bookings.aircraft.service;

import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;
import ru.katiafill.bookings.shared.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface SeatService {
    Seat getSeat(String aircraftCode, String seatNo) throws ResourceNotFoundException;
    List<Seat> getAllSeats(String aircraftCode);
    List<Seat> getSeatsByFareConditions(String aircraftCode, FareConditions conditions);
    /* Получить места для лайнера, сгруппированные по классам обслуживания.*/
    Map<FareConditions, List<String>> getGroupedSeats(String aircraftCode);

    List<Seat> addSeats(List<Seat> seats, String aircraftCode) throws ResourceAlreadyExistsException;
    List<Seat> updateSeats(List<Seat> seats, String aircraftCode);
    void deleteSeats(List<Seat> seats, String aircraftCode);
}
