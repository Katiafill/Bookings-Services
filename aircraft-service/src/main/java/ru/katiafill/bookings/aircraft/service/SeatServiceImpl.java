package ru.katiafill.bookings.aircraft.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.Seat;
import ru.katiafill.bookings.aircraft.repository.SeatRepository;
import ru.katiafill.bookings.shared.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;

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
    public Seat getSeat(String aircraftCode, String seatNo) throws ResourceNotFoundException {
        return seatRepository.findById(new Seat.SeatPK(aircraftCode, seatNo))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat at aircraft id=" + aircraftCode +
                                " and seatNo=" + seatNo + " not found."));
    }

    @Override
    public List<Seat> getAllSeats(String aircraftCode) {
        return seatRepository.findAllByAircraftCode(aircraftCode);
    }

    @Override
    public List<Seat> getSeatsByFareConditions(String aircraftCode, FareConditions conditions) {
        return seatRepository.findAllByAircraftCodeAndFareConditions(aircraftCode, conditions);
    }

    @Override
    public Map<FareConditions, List<String>> getGroupedSeats(String aircraftCode) {
        List<Seat> seats = getAllSeats(aircraftCode);

        return seats.stream()
                .collect(Collectors.groupingBy(Seat::getFareConditions,
                        Collectors.mapping(Seat::getSeatNo, Collectors.toList())));
    }

    @Override
    public List<Seat> addSeats(List<Seat> seats, String aircraftCode) throws ResourceAlreadyExistsException {
        // Проверку на наличие такого самолета проведет сама БД по первичному ключу.
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
        // Проверку на наличие такого самолета проведет сама БД по первичному ключу.
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
        // Если такой записи нет, то ничего не удалит.
        // Нужно ли сообщать об этом пользователю?
        seats.forEach(s -> s.setAircraftCode(aircraftCode));
        seatRepository.deleteAll(seats);
    }
}
