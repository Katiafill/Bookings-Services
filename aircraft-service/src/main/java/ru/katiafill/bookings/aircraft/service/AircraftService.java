package ru.katiafill.bookings.aircraft.service;

import ru.katiafill.bookings.aircraft.exception.DatabaseException;
import ru.katiafill.bookings.aircraft.exception.ResourceNotFoundException;
import ru.katiafill.bookings.aircraft.model.Aircraft;

import java.util.List;

public interface AircraftService {
    List<Aircraft> getAircrafts() throws DatabaseException;
    Aircraft getAircraft(String id) throws DatabaseException, ResourceNotFoundException;
    Aircraft createAircraft(Aircraft aircraft) throws DatabaseException;
    Aircraft updateAircraft(Aircraft aircraft) throws DatabaseException;
    void deleteAircraft(String aircraftCode) throws DatabaseException;


}
