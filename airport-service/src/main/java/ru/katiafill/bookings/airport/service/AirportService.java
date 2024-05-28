package ru.katiafill.bookings.airport.service;

import ru.katiafill.bookings.shared.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;
import ru.katiafill.bookings.airport.model.Airport;

import java.util.List;

public interface AirportService {
    List<Airport> getAirports();
    Airport getAirport(String code) throws ResourceNotFoundException;
    Airport createAirport(Airport airport) throws ResourceAlreadyExistsException;
    Airport updateAirport(Airport airport) throws ResourceNotFoundException;
    void deleteAirport(String code) throws ResourceNotFoundException;
}
