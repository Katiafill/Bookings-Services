package ru.katiafill.bookings.aircraft.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.katiafill.bookings.aircraft.model.Aircraft;

import java.util.Optional;

@Repository
public interface AircraftRepository extends ListCrudRepository<Aircraft, String> {
    /* Получение самолета по модели.
     * Название модели передается в одной из локали (ru, en). */
    @Query(value = "select * from {h-schema}aircrafts_data " +
            "where model->>'en' = :model or model->>'ru' = :model",
            nativeQuery = true)
    Optional<Aircraft> findByModel(@Param("model") String model);
}
