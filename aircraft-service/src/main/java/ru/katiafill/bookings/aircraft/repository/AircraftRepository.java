package ru.katiafill.bookings.aircraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.katiafill.bookings.aircraft.model.Aircraft;

import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, String> {
    /* Получение самолета по модели.
     * Название модели передается в одной из локали (ru, en). */
    @Query(value = "select * from {h-schema}aircrafts_data " +
            "where model->>'en' = :model or model->>'ru' = :model",
            nativeQuery = true)
    Optional<Aircraft> findByModel(@Param("model") String model);
}
