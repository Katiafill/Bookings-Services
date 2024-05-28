package ru.katiafill.bookings.flight.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katiafill.bookings.flight.model.Aircraft;
import ru.katiafill.bookings.flight.model.Airport;
import ru.katiafill.bookings.flight.model.Flight;
import ru.katiafill.bookings.flight.model.FlightStatus;
import ru.katiafill.bookings.flight.repository.FlightRepository;
import ru.katiafill.bookings.flight.service.client.AircraftClient;
import ru.katiafill.bookings.flight.service.client.AirportClient;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {
    private final FlightRepository repository;

    private final AircraftClient aircraftClient;
    private final AirportClient airportClient;

    @Override
    public List<Flight> getFlightsByFlightNo(String flightNo) {
        return repository.findAllByFlightNo(flightNo);
    }

    @Override
    public List<Flight> getFlightsByStatus(FlightStatus status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public Flight getFlight(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Flight with id = " + id + " not found.")
        );
    }

    @Override
    public Flight createFlight(Flight flight) {
        flight.setId(null);
        Aircraft aircraft = aircraftClient.getAircraft(flight.getAircraftCode());
        log.info("Aircraft: {}", aircraft);
        Airport departureAirport = airportClient.getAirport(flight.getDepartureAirportCode());
        Airport arrivalAirport = airportClient.getAirport(flight.getArrivalAirportCode());
        log.info("Departure airport: {}, arrival airport: {}", departureAirport, arrivalAirport);
        return repository.save(flight);
    }

    @Override
    public Flight updateFlight(Flight flight) throws ResourceNotFoundException {
        return repository.save(flight);
    }

    @Override
    public Flight updateStatus(Long id, FlightStatus status) throws ResourceNotFoundException {
        repository.updateStatus(id, status);
        return getFlight(id);
    }

    @Override
    public Flight updateActualDeparture(Long id, ZonedDateTime actualDeparture) throws ResourceNotFoundException {
        repository.updateActualDeparture(id, actualDeparture);
        return getFlight(id);
    }

    @Override
    public Flight updateActualArrival(Long id, ZonedDateTime actualArrival) throws ResourceNotFoundException {
        repository.updateActualArrival(id, actualArrival);
        return getFlight(id);
    }

    @Override
    public void deleteFlight(Long id) throws ResourceNotFoundException {
        repository.deleteById(id);
    }
}
