package ru.katiafill.bookings.flight.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.katiafill.bookings.flight.exception.FeignErrorDecoder;
import ru.katiafill.bookings.flight.model.Airport;

import java.util.List;

@FeignClient(name = "airport-service",
        configuration = {FeignErrorDecoder.class})
public interface AirportClient {
    @RequestMapping(value = "/api/airport", method = RequestMethod.GET)
    List<Airport> getAirports();

    @RequestMapping(value = "/api/airport/{id}", method = RequestMethod.GET)
    Airport getAirport(@PathVariable String id);
}
