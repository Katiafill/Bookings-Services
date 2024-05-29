package ru.katiafill.bookings.flight.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.katiafill.bookings.flight.exception.InvalidFlightRequestException;
import ru.katiafill.bookings.flight.model.Aircraft;
import ru.katiafill.bookings.flight.model.Airport;
import ru.katiafill.bookings.flight.model.Flight;
import ru.katiafill.bookings.flight.service.client.AircraftClient;
import ru.katiafill.bookings.flight.service.client.AirportClient;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
class FlightCheckService {
    private final AircraftClient aircraftClient;
    private final AirportClient airportClient;

    void check(@NotNull Flight flight) throws InvalidFlightRequestException {
        Aircraft aircraft = getAircraft(flight.getAircraftCode());
        checkAirports(flight.getDepartureAirportCode(), flight.getArrivalAirportCode());

        checkDates(flight);

        // TODO: другие проверки по желанию)
    }

    private void checkDates(Flight flight) {
        ZonedDateTime scheduleDeparture = flight.getScheduledDeparture();
        ZonedDateTime scheduleArrival = flight.getScheduledArrival();
        if (scheduleArrival.isBefore(scheduleDeparture)) {
            throw new InvalidFlightRequestException("Arrival date should be after departure date");
        }

        ZonedDateTime actualDeparture = flight.getActualDeparture();
        if (actualDeparture != null &&
                (!scheduleDeparture.isEqual(actualDeparture) ||
                        scheduleDeparture.isAfter(actualDeparture))) {
            throw new InvalidFlightRequestException("Actual departure date is invalid.");
        }

        ZonedDateTime actualArrival = flight.getActualArrival();
        if (actualArrival != null && actualArrival.isBefore(actualDeparture)) {
            throw new InvalidFlightRequestException("Actual arrival date is invalid.");
        }
    }

    private void checkAirports(String dAirportCode, String aAirportCode) {
        Airport departureAirport = getAirport(dAirportCode);
        Airport arrivalAirport = getAirport(aAirportCode);

        if (departureAirport.equals(arrivalAirport) ||
                departureAirport.getCity().equals(arrivalAirport.getCity())) {
            throw new InvalidFlightRequestException("Airports should be different and do not in same city.");
        }
    }

    Aircraft getAircraft(@NotNull String aircraftCode) throws InvalidFlightRequestException {
        try {
            Aircraft aircraft = aircraftClient.getAircraft(aircraftCode);
            log.info("Flight aircraft: {}", aircraft);
            return aircraft;
        } catch (ResourceNotFoundException ex) {
            throw new InvalidFlightRequestException("Invalid flight data.", ex);
        }
    }

    Airport getAirport(@NotNull String airportCode) throws InvalidFlightRequestException {
        try {
            Airport airport = airportClient.getAirport(airportCode);
            log.info("Flight airport: {}", airport);
            return airport;
        } catch (ResourceNotFoundException ex) {
            throw new InvalidFlightRequestException("Invalid flight data.", ex);
        }
    }

}
