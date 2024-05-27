package ru.katiafill.bookings.flight.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class FlightStatusConverter implements AttributeConverter<FlightStatus, String> {
    @Override
    public String convertToDatabaseColumn(FlightStatus attribute) {
        return attribute != null ? attribute.getName() : null;
    }

    @Override
    public FlightStatus convertToEntityAttribute(String dbData) {
        return dbData != null ?
                Stream.of(FlightStatus.values())
                        .filter(s -> s.getName().equals(dbData))
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new)
                        : null;

    }
}
