package ru.katiafill.bookings.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.katiafill.bookings.flight.model.Flight;
import ru.katiafill.bookings.flight.model.FlightStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByFlightNo(String flightNo);
    List<Flight> findAllByStatus(FlightStatus status);
    List<Flight> findAllByDepartureAirportCodeAndArrivalAirportCode(String departureAirport, String arrivalAirport);
    List<Flight> findAllByAircraftCode(String aircraftCode);

    @Modifying
    @Query("update Flight f set f.status = :status where f.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") FlightStatus status);

    @Modifying
    @Query("update Flight f set f.status = 'Departed', f.actualDeparture = :departure where f.id = :id")
    void updateActualDeparture(@Param("id") Long id, @Param("departure") ZonedDateTime actualDeparture);

    @Modifying
    @Query("update Flight f set f.status = 'Arrived', f.actualArrival = :arrival where f.id = :id")
    void updateActualArrival(@Param("id") Long id, @Param("arrival") ZonedDateTime actualArrival);
}
