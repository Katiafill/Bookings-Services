package ru.katiafill.bookings.aircraft.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.katiafill.bookings.aircraft.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.aircraft.exception.ResourceNotFoundException;
import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.LocalizedString;
import ru.katiafill.bookings.aircraft.model.Seat;
import ru.katiafill.bookings.aircraft.repository.AircraftRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AircraftServiceTest {

    @Autowired
    private AircraftService service;

    @MockBean
    private AircraftRepository repository;

    @MockBean
    private SeatService seatService;

    private Aircraft aircraft;
    private Seat economySeat, comfortSeat, businessSeat;
    private String aircraftCode;

    @BeforeEach
    public void setUp() {
        aircraftCode = "SMP";

        economySeat = new Seat(aircraftCode, "A1", FareConditions.Economy);
        comfortSeat = new Seat(aircraftCode, "B1", FareConditions.Comfort);
        businessSeat = new Seat(aircraftCode, "C1", FareConditions.Business);

        aircraft = Aircraft.builder()
                .code(aircraftCode)
                .model(new LocalizedString("Sample", "Пример"))
                .range(1000)
                .build();

    }

    @Test
    void getAircrafts() {
        when(repository.findAll()).thenReturn(List.of(aircraft));

        List<Aircraft> aircrafts = service.getAircrafts();

        assertNotNull(aircrafts);
        assertEquals(aircrafts.size(), 1);
        assertEquals(aircrafts.get(0), aircraft);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAircraft() {
        when(repository.findById(aircraftCode)).thenReturn(Optional.of(aircraft));

        Aircraft findedAircraft = service.getAircraft(aircraftCode);

        assertNotNull(findedAircraft);
        assertEquals(findedAircraft, aircraft);
        verify(repository, times(1))
                .findById(aircraftCode);
        verify(seatService, times(0))
                .getAllSeats(any());
    }

    @Test
    void getAircraft_invalidId() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
           service.getAircraft("123");
        });

        assertTrue(ex.getMessage().contains("id=123"));
        verify(repository, times(1)).findById("123");
    }


    @Test
    void getFullAircraft() {
        when(repository.findById(any()))
                .thenReturn(Optional.of(aircraft));
        when(seatService.getAllSeats(any()))
                .thenReturn(List.of(economySeat, comfortSeat, businessSeat));

        Aircraft findedAircraft = service.getAircraft(aircraftCode, true);

        assertNotNull(findedAircraft);
        assertEquals(findedAircraft, aircraft);
        assertNotNull(findedAircraft.getSeats());
        assertEquals(findedAircraft.getSeats().size(), 3);

        verify(repository, times(1))
                .findById(any());
        verify(seatService, times(1))
                .getAllSeats(any());
    }

    @Test
    void createAircraft() {
        when(repository.save(any())).thenReturn(aircraft);

        Aircraft saved = service.createAircraft(aircraft);

        assertEquals(saved, aircraft);

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
        verify(seatService, times(0)).addSeats(any(), any());
    }

    @Test
    void createAircraftWithSeats() {
        List<Seat> seats =  List.of(economySeat, comfortSeat, businessSeat);
        when(repository.save(any())).thenReturn(aircraft);
        aircraft.setSeats(seats);

        Aircraft saved = service.createAircraft(aircraft);

        assertEquals(saved, aircraft);

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
        verify(seatService, times(1)).addSeats(any(), any());
    }

    @Test
    void createAircraft_alreadyExists() {
        when(repository.findById(any()))
                .thenReturn(Optional.of(aircraft));

        Exception ex = assertThrows(ResourceAlreadyExistsException.class, () -> {
            service.createAircraft(aircraft);
        });

        assertTrue(ex.getMessage().contains("id=" + aircraft.getCode()));

        verify(repository, times(1)).findById(any());
        verify(repository, times(0)).save(any());
        verify(seatService, times(0)).addSeats(any(), any());
    }

    @Test
    void updateAircraft() {
        when(repository.findById(any())).thenReturn(Optional.of(aircraft));
        when(repository.save(any())).thenReturn(aircraft);

        Aircraft saved = service.updateAircraft(aircraft);

        assertEquals(saved, aircraft);

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
        verify(seatService, times(0)).updateSeats(any(), any());
    }

    @Test
    void updateAircraftWithSeats() {
        when(repository.findById(any())).thenReturn(Optional.of(aircraft));
        when(repository.save(any())).thenReturn(aircraft);
        aircraft.setSeats(List.of(economySeat, comfortSeat, businessSeat));

        Aircraft saved = service.updateAircraft(aircraft);

        assertEquals(saved, aircraft);

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
        verify(seatService, times(1)).updateSeats(any(), any());
    }

    @Test
    void updateAircraft_noAircraft() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.updateAircraft(aircraft);
        });

        assertTrue(ex.getMessage().contains("id=" + aircraftCode));

        verify(repository, times(1)).findById(any());
        verify(repository, times(0)).save(any());
        verify(seatService, times(0)).updateSeats(any(), any());
    }

    @Test
    void deleteAircraft() {
        when(repository.findById(any())).thenReturn(Optional.of(aircraft));

        service.deleteAircraft(aircraftCode);

        verify(repository, times(1)).delete(aircraft);
    }

    @Test
    void deleteAircraft_NoAircraft() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteAircraft(aircraftCode);
        });

        assertTrue(ex.getMessage().contains("id=" + aircraftCode));

        verify(repository, times(0)).delete(aircraft);
    }
}