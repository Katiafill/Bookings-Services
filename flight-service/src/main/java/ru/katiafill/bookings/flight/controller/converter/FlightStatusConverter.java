package ru.katiafill.bookings.flight.controller.converter;

import org.springframework.core.convert.converter.Converter;
import ru.katiafill.bookings.flight.model.FlightStatus;

import java.util.stream.Stream;

public class FlightStatusConverter implements Converter<String, FlightStatus> {
    @Override
    public FlightStatus convert(String source) {
        return Stream.of(FlightStatus.values())
                .filter(s -> s.getName().equals(source))
                .findFirst().orElse(null);
    }
}
