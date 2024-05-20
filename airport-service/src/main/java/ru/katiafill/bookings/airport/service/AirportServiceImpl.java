package ru.katiafill.bookings.airport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katiafill.bookings.airport.exception.ResourceAlreadyExistsException;
import ru.katiafill.bookings.airport.exception.ResourceNotFoundException;
import ru.katiafill.bookings.airport.model.Airport;
import ru.katiafill.bookings.airport.repository.AirportRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AirportServiceImpl implements AirportService {

    private AirportRepository repository;

    @Override
    public List<Airport> getAirports() {
        return repository.findAll();
    }

    @Override
    public Airport getAirport(String code) throws ResourceNotFoundException {
        return repository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Airport with id=" + code + " not found."));
    }

    @Override
    public Airport createAirport(Airport airport) throws ResourceAlreadyExistsException {
        Optional<Airport> sameAirport = repository.findById(airport.getCode());
        if (sameAirport.isPresent()) {
            throw new ResourceAlreadyExistsException("Airport with id=" + airport.getCode() + " already exist.");
        }

        return repository.save(airport);
    }

    @Override
    public Airport updateAirport(Airport airport) throws ResourceNotFoundException {
        Airport saved = getAirport(airport.getCode());

        return repository.save(airport);
    }

    @Override
    public void deleteAirport(String code) throws ResourceNotFoundException {
        Airport saved = getAirport(code);
        repository.delete(saved);
    }
}
