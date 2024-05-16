package ru.katiafill.bookings.aircraft.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katiafill.bookings.aircraft.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.aircraft.exception.ResourceNotFoundException;
import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.aircraft.repository.AircraftRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final SeatService seatService;

    @Override
    public List<Aircraft> getAircrafts() {
        return aircraftRepository.findAll();
    }

    @Override
    public Aircraft getAircraft(String id) throws ResourceNotFoundException {
        return getAircraft(id, false);
    }

    @Override
    public Aircraft getAircraft(String id, boolean full) throws ResourceNotFoundException {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft with id=" + id + " not found."));

        if (full) {
            aircraft.setSeats(seatService.getAllSeats(id));
        }

        return aircraft;
    }

    @Override
    /* Сохраняем модель самолетв в БД.
    * Если самолет с таким идентификатором существует, то выбрасываем исключение ResourceAlreadyExistsException.
    * */
    public Aircraft createAircraft(Aircraft aircraft) throws ResourceAlreadyExistsException {
        Optional<Aircraft> sameAircraft = aircraftRepository.findById(aircraft.getCode());
        if (sameAircraft.isPresent()) {
            throw new ResourceAlreadyExistsException("Aircraft with id=" + aircraft.getCode() + " already exists.");
        }

        Aircraft saved = aircraftRepository.save(aircraft);

        if (aircraft.getSeats() != null) {
            saved.setSeats(seatService.addSeats(aircraft.getSeats(), aircraft.getCode()));
        }

        return saved;
    }

    @Override
    public Aircraft updateAircraft(Aircraft aircraft) throws ResourceNotFoundException {
            Optional<Aircraft> sameAircraft = aircraftRepository.findById(aircraft.getCode());
            if (sameAircraft.isEmpty()) {
                throw new ResourceNotFoundException("Aircraft with id=" + aircraft.getCode() + "not found.");
            }

        Aircraft saved = aircraftRepository.save(aircraft);

        if (aircraft.getSeats() != null) {
            saved.setSeats(seatService.updateSeats(aircraft.getSeats(), aircraft.getCode()));
        }

        return saved;
    }

    @Override
    public void deleteAircraft(String aircraftCode) throws ResourceNotFoundException {
        Optional<Aircraft> sameAircraft = aircraftRepository.findById(aircraftCode);
        if (sameAircraft.isEmpty()) {
            throw new ResourceNotFoundException("Aircraft with id= " + aircraftCode + " not found.");
        }

        // Удалять места из БД не нужно, т.к база данных сама удалит связанные данные.
        aircraftRepository.deleteById(aircraftCode);
    }

}
