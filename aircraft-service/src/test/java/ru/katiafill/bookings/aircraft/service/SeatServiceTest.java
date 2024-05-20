package ru.katiafill.bookings.aircraft.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.katiafill.bookings.aircraft.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.aircraft.exception.ResourceNotFoundException;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;
import ru.katiafill.bookings.aircraft.repository.SeatRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SeatServiceTest {

    @Autowired
    private SeatService service;

    @MockBean
    private SeatRepository repository;

    private Seat economySeat, comfortSeat, businessSeat;
    private String aircraftCode;

    @BeforeEach
    void setUp() {
        aircraftCode = "SMP";

        economySeat = new Seat(aircraftCode, "A1", FareConditions.Economy);
        comfortSeat = new Seat(aircraftCode, "B1", FareConditions.Comfort);
        businessSeat = new Seat(aircraftCode, "C1", FareConditions.Business);
    }

    @Test
    void getSeat() {
        when(repository.findById(any())).thenReturn(Optional.of(economySeat));

        Seat seat = service.getSeat(aircraftCode, economySeat.getSeatNo());

        assertEquals(seat, economySeat);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void getSeat_notFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getSeat(aircraftCode, economySeat.getSeatNo());
        });

        assertTrue(ex.getMessage().contains("seatNo=" + economySeat.getSeatNo()));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void getAllSeats() {
        List<Seat> seats = List.of(economySeat, comfortSeat, businessSeat);
        when(repository.findAllByAircraftCode(any()))
                .thenReturn(seats);

        List<Seat> foundSeats = service.getAllSeats(aircraftCode);

        assertEquals(foundSeats, seats);

        verify(repository, times(1)).findAllByAircraftCode(any());
    }

    @Test
    void getSeatsByFareConditions() {
        testSeatsByFareCondition(FareConditions.Economy, economySeat);
        testSeatsByFareCondition(FareConditions.Comfort, comfortSeat);
        testSeatsByFareCondition(FareConditions.Business, businessSeat);
    }

    private void testSeatsByFareCondition(FareConditions conditions, Seat actual) {
        when(repository.findAllByAircraftCodeAndFareConditions(any(), any())).thenReturn(List.of(actual));

        List<Seat> seatList = service.getSeatsByFareConditions(aircraftCode, conditions);

        assertNotNull(seatList);
        assertEquals(seatList.size(), 1);
        assertEquals(seatList.get(0), actual);

        verify(repository, times(1)).findAllByAircraftCodeAndFareConditions(actual.getAircraftCode(), conditions);
    }

    @Test
    void getGroupedSeats() {
        when(repository.findAllByAircraftCode(any()))
                .thenReturn(List.of(economySeat, comfortSeat, businessSeat));

        Map<FareConditions, List<String>> actualSeats = Map.of(
                FareConditions.Economy, List.of(economySeat.getSeatNo()),
                FareConditions.Comfort, List.of(comfortSeat.getSeatNo()),
                FareConditions.Business, List.of(businessSeat.getSeatNo())
        );

        Map<FareConditions, List<String>> seats = service.getGroupedSeats(aircraftCode);
        assertEquals(seats, actualSeats);
    }

    @Test
    void addSeats() {
        List<Seat> seats = List.of(economySeat);
        when(repository.findAllById(anyCollection())).thenReturn(List.of());
        when(repository.saveAll(anyCollection())).thenReturn(seats);

        List<Seat> saved = service.addSeats(seats, aircraftCode);

        assertEquals(saved, seats);

        verify(repository, times(1)).findAllById(anyCollection());
        verify(repository, times(1)).saveAll(anyCollection());
    }

    @Test
    void addSeats_alreadyExists() {
        List<Seat> seats = List.of(economySeat);
        when(repository.findAllById(anyCollection())).thenReturn(seats);

        Exception ex = assertThrows(ResourceAlreadyExistsException.class, () -> {
            service.addSeats(seats, aircraftCode);
        });

        assertTrue(ex.getMessage().contains(seats.toString()));

        verify(repository, times(1)).findAllById(anyCollection());
        verify(repository, times(0)).saveAll(anyCollection());
    }

    @Test
    void updateSeats() {
        List<Seat> seats = List.of(economySeat);
        when(repository.findAllByAircraftCode(any())).thenReturn(seats);
        when(repository.saveAll(anyCollection())).thenReturn(seats);

        List<Seat> saved = service.updateSeats(seats, aircraftCode);

        assertEquals(saved, seats);

        verify(repository, times(1)).findAllByAircraftCode(aircraftCode);
        verify(repository, times(1)).saveAll(seats);
        verify(repository, times(1)).deleteAll(List.of());
    }

    @Test
    void updateSeats_withDelete() {
        List<Seat> savedSeats = List.of(economySeat, comfortSeat);
        List<Seat> updatedSeats = List.of(economySeat);
        List<Seat> deletedSeats = List.of(comfortSeat);

        when(repository.findAllByAircraftCode(aircraftCode))
                .thenReturn(savedSeats);
        when(repository.saveAll(updatedSeats))
                .thenReturn(updatedSeats);

        List<Seat> seats = service.updateSeats(updatedSeats, aircraftCode);
        assertEquals(seats, updatedSeats);

        verify(repository, times(1))
                .findAllByAircraftCode(aircraftCode);
        verify(repository, times(1))
                .saveAll(updatedSeats);
        verify(repository, times(1))
                .deleteAll(deletedSeats);
    }

    @Test
    void deleteSeats() {
        List<Seat> seats = List.of(economySeat);
        service.deleteSeats(seats, aircraftCode);
        verify(repository, times(1)).deleteAll(seats);
    }
}