package ru.katiafill.bookings.flight.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.katiafill.bookings.flight.exception.FeignErrorDecoder;
import ru.katiafill.bookings.flight.model.Aircraft;

import java.util.List;

@FeignClient(name = "aircraft-service",
        configuration = {FeignErrorDecoder.class})
public interface AircraftClient {
    @RequestMapping(value = "/api/aircraft", method = RequestMethod.GET)
    List<Aircraft> getAircrafts();

    @RequestMapping(value = "/api/aircraft/{id}", method = RequestMethod.GET)
    Aircraft getAircraft(@PathVariable String id);
}
