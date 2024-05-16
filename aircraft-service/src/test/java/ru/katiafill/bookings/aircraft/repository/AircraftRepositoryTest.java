package ru.katiafill.bookings.aircraft.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.aircraft.model.FareConditions;
import ru.katiafill.bookings.aircraft.model.LocalizedString;
import ru.katiafill.bookings.aircraft.model.Seat;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AircraftRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AircraftRepository repository;

    @Autowired
    private SeatRepository seatRepository;

    private Aircraft aircraft;

    @BeforeEach
    void setUp() {
        Seat seat = new Seat("SMP", "A1", FareConditions.Economy);

        aircraft = Aircraft.builder()
                .code("SMP")
                .model(new LocalizedString("Sample", "Пример"))
                .range(1000)
                .build();

        entityManager.persist(aircraft);
        entityManager.persist(seat);
        entityManager.flush();
    }

    @Test
    void findByModel() {
        Optional<Aircraft> optionalAircraft = repository.findByModel(aircraft.getModel().getRu());
        assertTrue(optionalAircraft.isPresent());
        assertEquals(optionalAircraft.get(), aircraft);
    }

    @Test
    void deleteAircraftWithSeats() {
        // Проверить, что работает каскадное удаление,
        // при удалении самолета - удаляются связанные с ним места.
        repository.delete(aircraft);
        entityManager.flush();

        Optional<Aircraft> aircraftOptional = repository.findById(aircraft.getCode());
        assertTrue(aircraftOptional.isEmpty());

        List<Seat> seats = seatRepository.findAllByAircraftCode(aircraft.getCode());
        assertTrue(seats.isEmpty());
    }
}