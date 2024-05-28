package ru.katiafill.bookings.flight.service;

import ru.katiafill.bookings.flight.model.Flight;
import ru.katiafill.bookings.flight.model.FlightStatus;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

public interface FlightService {
    List<Flight> getFlightsByFlightNo(String flightNo);
    List<Flight> getFlightsByStatus(FlightStatus status);

    Flight getFlight(Long id) throws ResourceNotFoundException;

    Flight createFlight(Flight flight);
    Flight updateFlight(Flight flight) throws ResourceNotFoundException;
    Flight updateStatus(Long id, FlightStatus status) throws ResourceNotFoundException;
    Flight updateActualDeparture(Long id, ZonedDateTime actualDeparture) throws ResourceNotFoundException;
    Flight updateActualArrival(Long id, ZonedDateTime actualArrival) throws ResourceNotFoundException;

    void deleteFlight(Long id) throws ResourceNotFoundException;
}
