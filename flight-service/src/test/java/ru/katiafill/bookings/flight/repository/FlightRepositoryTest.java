package ru.katiafill.bookings.flight.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.katiafill.bookings.flight.model.Flight;
import ru.katiafill.bookings.flight.model.FlightStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/test_init.sql")
class FlightRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FlightRepository repository;

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = Flight.builder()
                .flightNo("SM1234")
                .scheduledDeparture(ZonedDateTime.of(2024, 5,28, 12, 30, 0, 0, ZoneId.systemDefault()))
                .scheduledArrival(ZonedDateTime.of(2024, 5,28, 16, 30, 0, 0, ZoneId.systemDefault()))
                .status(FlightStatus.SCHEDULED)
                .aircraftCode("773")
                .departureAirportCode("OVB")
                .arrivalAirportCode("DME")
                .build();

        flight = entityManager.persist(flight);
    }

    @Test
    public void findAllByFlightNo() {
        List<Flight> flights = repository.findAllByFlightNo(flight.getFlightNo());

        assertEquals(flights.size(), 1);
        assertEquals(flights.get(0), flight);
    }

    @Test
    void findAllByStatus() {
        List<Flight> flights = repository.findAllByStatus(FlightStatus.SCHEDULED);

        assertEquals(flights.size(), 1);
        assertEquals(flights.get(0), flight);
    }

    @Test
    void findAllByDepartureAirportAndArrivalAirport() {
        List<Flight> flights = repository.findAllByDepartureAirportCodeAndArrivalAirportCode(
                flight.getDepartureAirportCode(),
                flight.getArrivalAirportCode());

        assertEquals(flights.size(), 1);
        assertEquals(flights.get(0), flight);
    }

    @Test
    void findAllByAircraftCode() {
        List<Flight> flights = repository.findAllByAircraftCode(flight.getAircraftCode());

        assertEquals(flights.size(), 1);
        assertEquals(flights.get(0), flight);
    }

    @Test
    void updateStatus() {
        repository.updateStatus(flight.getId(), FlightStatus.ARRIVED);

        Optional<Flight> updated = repository.findById(flight.getId());
        assertTrue(updated.isPresent());

        flight.setStatus(FlightStatus.ARRIVED);
        assertEquals(updated.get(), flight);
    }

    @Test
    void updateActualDeparture() {
        repository.updateActualDeparture(flight.getId(), flight.getScheduledDeparture());

        Optional<Flight> updated = repository.findById(flight.getId());
        assertTrue(updated.isPresent());

        flight.setStatus(FlightStatus.DEPARTED);
        flight.setActualDeparture(flight.getScheduledDeparture());
        assertEquals(updated.get(), flight);
    }

    @Test
    void updateActualArrival() {
        // Установить время отправки, иначе возникает ошибка проверки ограничений.
        flight.setActualDeparture(flight.getScheduledDeparture());

        repository.updateActualArrival(flight.getId(), flight.getScheduledArrival());

        Optional<Flight> updated = repository.findById(flight.getId());
        assertTrue(updated.isPresent());

        flight.setStatus(FlightStatus.ARRIVED);
        flight.setActualArrival(flight.getScheduledArrival());
        assertEquals(updated.get(), flight);
    }
}