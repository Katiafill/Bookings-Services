package ru.katiafill.bookings.aircraft.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katiafill.bookings.aircraft.exception.DatabaseException;
import ru.katiafill.bookings.aircraft.exception.ResourceAlreadyExistsException;
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

    @Override
    public List<Seat> addSeats(List<Seat> seats, String aircraftCode) {
        if (seats.isEmpty()) {
            return List.of();
        }

        // Установим всем идентификатор самолета.
        seats.forEach(s -> s.setAircraftCode(aircraftCode));

        // Найдем, есть ли такие места в базе.
        List<Seat> allSeats = seatRepository.findAllById(seats
                .stream()
                .map(s -> new Seat.SeatPK(s.getAircraftCode(), s.getSeatNo()))
                .collect(Collectors.toList()));

        // Если хоть что-то уже создано, то откатываемся, и ничего не сохраняем.
        if (!allSeats.isEmpty()) {
            throw new ResourceAlreadyExistsException("Seats for aircraft id=" + aircraftCode +
                    "already exist: " + allSeats);
        }

        return seatRepository.saveAll(seats);
    }

    @Override
    // Метод для обновления списка мест у самолета.
    // Если в списке новые места, они добавляются,
    // текущие обновляются,
    // если не содержит мест, которые есть, то те удаляются.
    public List<Seat> updateSeats(List<Seat> seats, String aircraftCode) {
        // Установим всем идентификатор самолета.
        seats.forEach(s -> s.setAircraftCode(aircraftCode));

        // Получим все места, которые сохранены для этого самолета.
        List<Seat> allSeats = seatRepository.findAllByAircraftCode(aircraftCode);

        // Отберем те места, которых нет в обновленном списке, и удалим их из БД.
        List<Seat> toDeleteSeats = allSeats.stream().filter(s -> !seats.contains(s)).toList();
        deleteSeats(toDeleteSeats, aircraftCode);

        // Сохраним новые места и измененные в БД.
        return seatRepository.saveAll(seats);
    }

    @Override
    public void deleteSeats(List<Seat> seats, String aircraftCode) {
        seats.forEach(s -> s.setAircraftCode(aircraftCode));
        seatRepository.deleteAll(seats);
    }
}
