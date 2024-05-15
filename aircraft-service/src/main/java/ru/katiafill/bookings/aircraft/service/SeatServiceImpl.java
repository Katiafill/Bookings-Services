package ru.katiafill.bookings.aircraft.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katiafill.bookings.aircraft.exception.DatabaseException;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;
import ru.katiafill.bookings.aircraft.repository.SeatRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Override
    public List<Seat> getAllSeats(String aircraftCode) throws DatabaseException {
        try {
            return seatRepository.findAllByAircraftCode(aircraftCode);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when find all seats for aircraft: " + aircraftCode, ex);
        }
    }

    @Override
    public List<Seat> getSeatsByFareConditions(String aircraftCode, FareConditions conditions) throws DatabaseException {
        try {
            return seatRepository.findAllByAircraftCodeAndFareConditions(aircraftCode, conditions);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when find all seats for aircraft: " + aircraftCode + ", conditions: " + conditions, ex);
        }
    }

    @Override
    public Map<FareConditions, List<String>> getGroupedSeats(String aircraftCode) throws DatabaseException {
        List<Seat> seats = getAllSeats(aircraftCode);

        return seats.stream()
                .collect(Collectors.groupingBy(Seat::getFareConditions,
                        Collectors.mapping(Seat::getSeatNo, Collectors.toList())));
    }

    @Transactional
    @Override
    public void addSeats(List<Seat> seats, String aircraftCode) throws DatabaseException {
        if (seats.isEmpty()) {
            return;
        }
        // Установим всем идентификатор самолета.
        seats.forEach(s -> s.setAircraftCode(aircraftCode));

        try {
            // Найдем, есть ли такие места в базе.
            List<Seat> allSeats = seatRepository.findAllById(seats
                    .stream()
                    .map(s -> new Seat.SeatPK(s.getAircraftCode(), s.getSeatNo()))
                    .collect(Collectors.toList()));

            // Если хоть что-то уже создано, то откатываемся, и ничего не сохраняем.
            if (allSeats.isEmpty()) {
                seatRepository.saveAll(seats);
            } else {
                throw new DatabaseException("Exception occurred when save new seats for aircraft " + aircraftCode + ".\n" +
                        "Current seats already saved: " + allSeats);
            }
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when insert seats for aircraft " + aircraftCode, ex);
        }
    }

    @Override
    public void deleteSeats(List<Seat> seats, String aircraftCode) throws DatabaseException {
        if (seats.isEmpty()) {
            return;
        }

        seats.forEach(s -> s.setAircraftCode(aircraftCode));

        try {
            seatRepository.deleteAll(seats);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when delete seats for aircraft " + aircraftCode, ex);
        }
    }
}
