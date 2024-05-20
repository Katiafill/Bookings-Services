package ru.katiafill.bookings.airport.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.katiafill.bookings.airport.model.Airport;
import ru.katiafill.bookings.airport.model.LocalizedString;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends ListCrudRepository<Airport, String> {
    @Query(value = "SELECT * FROM  {h-schema}airports_data WHERE city->>'en' = :city OR city->>'ru' = :city", nativeQuery = true)
    List<Airport> findAllByCity(@Param("city") String city);
    List<Airport> findAllByTimezone(String timezone);
    @Query(value = "SELECT * FROM  {h-schema}airports_data WHERE airport_name->>'en' = :name OR airport_name->>'ru' = :name", nativeQuery = true)
    Optional<Airport> findByName(@Param("name") String name);

    @Query(value = "SELECT DISTINCT a.city FROM Airport a")
    List<LocalizedString> findAllCities();
}
