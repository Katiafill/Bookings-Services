package ru.katiafill.bookings.aircraft.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katiafill.bookings.aircraft.exception.DatabaseException;
import ru.katiafill.bookings.aircraft.exception.ResourceNotFoundException;
import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.aircraft.repository.AircraftRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;


    @Override
    public List<Aircraft> getAircrafts() throws DatabaseException {
        try {
            return aircraftRepository.findAll();
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when find all aircrafts", ex);
        }
    }

    @Override
    public Aircraft getAircraft(String id) throws DatabaseException, ResourceNotFoundException {
        try {
            return aircraftRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Aircraft with code " + id + " not found."));
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when find aircraft by id: " + id, ex);
        }
    }

    @Override
    public Aircraft createAircraft(Aircraft aircraft) throws DatabaseException {
        try {
            Aircraft saved = aircraftRepository.save(aircraft);
            log.info("Success created aircraft with id: {}", aircraft.getCode());
            return saved;
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when save aircraft", ex);
        }
    }

    @Override
    public Aircraft updateAircraft(Aircraft aircraft) throws DatabaseException {
        try {
            Aircraft saved = aircraftRepository.save(aircraft);
            log.info("Success updated aircraft with id: {}", aircraft.getCode());
            return saved;
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when save aircraft", ex);
        }
    }

    @Override
    public void deleteAircraft(String aircraftCode) throws DatabaseException {
        try {
            aircraftRepository.deleteById(aircraftCode);
            log.info("Success deleted aircraft by id: {}", aircraftCode);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Exception occurred when deleting an aircraft by id: " + aircraftCode, ex);
        }
    }

}
