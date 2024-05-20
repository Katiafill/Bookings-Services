package ru.katiafill.bookings.airport.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.katiafill.bookings.airport.model.Airport;
import ru.katiafill.bookings.airport.model.LocalizedString;
import ru.katiafill.bookings.airport.model.Point;
import ru.katiafill.bookings.airport.repository.AirportRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AirportServiceTest {

    @Autowired
    private AirportService service;

    @MockBean
    private AirportRepository repository;

    private Airport airport;

    @BeforeEach
    void setUp() {
        airport = Airport.builder()
                .code("SMP")
                .name(new LocalizedString("Sample", "Пример"))
                .city(new LocalizedString("Novosibirsk", "Новосибирск"))
                .timezone("Asia/Novosibirsk")
                .coordinates(new Point(1000, 1000))
                .build();
    }

    @Test
    void getAirports() {
        when(repository.findAll()).thenReturn(List.of(airport));

        List<Airport> airports = service.getAirports();

        assertNotNull(airports);
        assertEquals(airports.size(), 1);
        assertEquals(airports.get(0), airport);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAirport() {
        when(repository.findById(any())).thenReturn(Optional.of(airport));

        Airport findedAirport = service.getAirport(airport.getCode());

        assertNotNull(findedAirport);
        assertEquals(findedAirport, airport);
        verify(repository, times(1))
                .findById(airport.getCode());
    }

    @Test
    void createAirport() {
        when(repository.save(any())).thenReturn(airport);
        when(repository.findById(any())).thenReturn(Optional.empty());

        Airport saved = service.createAirport(airport);

        assertEquals(saved, airport);

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateAirport() {
        when(repository.findById(any())).thenReturn(Optional.of(airport));
        when(repository.save(any())).thenReturn(airport);

        Airport saved = service.updateAirport(airport);

        assertEquals(saved, airport);

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteAirport() {
        when(repository.findById(any())).thenReturn(Optional.of(airport));

        service.deleteAirport(airport.getCode());
        verify(repository, times(1)).delete(airport);
    }
}