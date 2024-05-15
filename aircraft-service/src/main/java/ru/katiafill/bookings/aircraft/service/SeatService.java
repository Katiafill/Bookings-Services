package ru.katiafill.bookings.aircraft.service;

import ru.katiafill.bookings.aircraft.exception.DatabaseException;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;

import java.util.List;
import java.util.Map;

public interface SeatService {
    List<Seat> getAllSeats(String aircraftCode) throws DatabaseException;
    List<Seat> getSeatsByFareConditions(String aircraftCode, FareConditions conditions) throws DatabaseException;
    /* Получить места для лайнера, сгруппированные по классам обслуживания.*/
    Map<FareConditions, List<String>> getGroupedSeats(String aircraftCode) throws DatabaseException;

    void addSeats(List<Seat> seats, String aircraftCode) throws DatabaseException;
    void deleteSeats(List<Seat> seats, String aircraftCode) throws DatabaseException;
}