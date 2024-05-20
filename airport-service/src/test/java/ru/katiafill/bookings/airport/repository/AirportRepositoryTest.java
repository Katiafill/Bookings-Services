package ru.katiafill.bookings.airport.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.katiafill.bookings.airport.model.Airport;
import ru.katiafill.bookings.airport.model.LocalizedString;
import ru.katiafill.bookings.airport.model.Point;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AirportRepositoryTest {

    @Autowired
    private AirportRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Airport airport;
    private String code, enCity, ruCity, enName, ruName, timezone;

    @BeforeEach
    void setUp() {
        code = "SMP";
        enCity = "Novosibirsk";
        ruCity = "Новосибирск";
        enName = "Sample";
        ruName = "Пример";
        timezone = "Asia/Novosibirsk";


        airport = Airport.builder()
                .code(code)
                .name(new LocalizedString(enName, ruName))
                .city(new LocalizedString(enCity, ruCity))
                .timezone(timezone)
                .coordinates(new Point(1000, 1000))
                .build();

        entityManager.persist(airport);
    }

    @Test
    void findAllByCity() {
        List<Airport> airports = repository.findAllByCity(enCity);
        assertEquals(airports.size(), 1);

        Airport enAirport = airports.get(0);
        assertEquals(enAirport, airport);

        airports = repository.findAllByCity(ruCity);
        assertEquals(airports.size(), 1);
        assertEquals(airports.get(0), enAirport);
    }

    @Test
    void findAllByTimezone() {
        List<Airport> airports = repository.findAllByTimezone(timezone);
        assertEquals(airports.size(), 1);
        assertEquals(airports.get(0), airport);
    }

    @Test
    void findByName() {
        Optional<Airport> optionalAirport = repository.findByName(enName);
        assertTrue(optionalAirport.isPresent());

        Airport enAirport = optionalAirport.get();
        assertEquals(enAirport, airport);

        optionalAirport = repository.findByName(ruName);
        assertTrue(optionalAirport.isPresent());
        assertEquals(optionalAirport.get(), enAirport);
    }

    @Test
    void findAllCities() {
        List<LocalizedString> cities = repository.findAllCities();
        assertEquals(cities.size(), 1);
        assertEquals(cities.get(0), new LocalizedString(enCity, ruCity));
    }
}