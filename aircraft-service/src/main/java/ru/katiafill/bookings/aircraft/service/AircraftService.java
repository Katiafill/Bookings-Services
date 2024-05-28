package ru.katiafill.bookings.aircraft.service;

import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.shared.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;

import java.util.List;

public interface AircraftService {
    List<Aircraft> getAircrafts();
    Aircraft getAircraft(String id) throws ResourceNotFoundException;
    Aircraft getAircraft(String id, boolean full) throws ResourceNotFoundException;
    Aircraft createAircraft(Aircraft aircraft) throws ResourceAlreadyExistsException;
    Aircraft updateAircraft(Aircraft aircraft) throws ResourceNotFoundException;
    void deleteAircraft(String aircraftCode) throws ResourceNotFoundException;


}
