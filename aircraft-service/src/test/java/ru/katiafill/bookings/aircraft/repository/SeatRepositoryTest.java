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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class SeatRepositoryTest {

    @Autowired
    private SeatRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Seat economySeat, comfortSeat, businessSeat;
    private String aircraftCode;

    @BeforeEach
    public void setUp() {
        aircraftCode = "SMP";

        // Создаем и сохраняем самолет, иначе БД не даст нам добавить места
        // из-за ограничений первичного ключа.
        Aircraft aircraft = Aircraft.builder()
                .code(aircraftCode)
                .model(new LocalizedString("Sample", "Пример"))
                .range(1000)
                .build();

        entityManager.persist(aircraft);

        economySeat = new Seat(aircraftCode, "A1", FareConditions.Economy);
        comfortSeat = new Seat(aircraftCode, "B1", FareConditions.Comfort);
        businessSeat = new Seat(aircraftCode, "C1", FareConditions.Business);

        entityManager.persist(economySeat);
        entityManager.persist(comfortSeat);
        entityManager.persist(businessSeat);
    }

    @Test
    void findAllByAircraftCode() {
        List<Seat> seats = repository.findAllByAircraftCode(aircraftCode);
        assertEquals(seats.size(), 3);
    }

    @Test
    void findAllByAircraftCodeAndFareConditions() {
        testFareConditions(FareConditions.Economy, economySeat);
        testFareConditions(FareConditions.Comfort, comfortSeat);
        testFareConditions(FareConditions.Business, businessSeat);
    }

    void testFareConditions(FareConditions conditions, Seat actual) {
        List<Seat> seats = repository.findAllByAircraftCodeAndFareConditions(aircraftCode, conditions);
        assertEquals(seats.size(), 1);
        assertEquals(seats.get(0), actual);
    }

}