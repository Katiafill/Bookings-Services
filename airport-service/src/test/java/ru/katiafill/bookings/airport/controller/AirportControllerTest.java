package ru.katiafill.bookings.airport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.katiafill.bookings.airport.model.Airport;
import ru.katiafill.bookings.airport.model.LocalizedString;
import ru.katiafill.bookings.airport.model.Point;
import ru.katiafill.bookings.airport.service.AirportService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AirportController.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AirportControllerTest {

    private static final String apiString = "/api/airport";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirportService service;

    private Airport airport;

    @BeforeEach
    void setUp() {
        airport = Airport.builder()
                .code("SMP")
                .name(new LocalizedString("Sample", "Пример"))
                .city(new LocalizedString("Novosibirsk", "Новосибирск"))
                .timezone("Asia/Novosibirsk")
                .coordinates(new Point(1000, 1000))
                .build();
    }

    @Test
    void getAirports() throws Exception {
        when(service.getAirports()).thenReturn(List.of(airport));

        mvc.perform(get(apiString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code").value(airport.getCode()))
                .andExpect(jsonPath("$[0].city").value(airport.getCity()))
                .andExpect(jsonPath("$[0].name").value(airport.getName()))
                .andExpect(jsonPath("$[0].coordinates").value(airport.getCoordinates()))
                .andExpect(jsonPath("$[0].timezone").value(airport.getTimezone()))
                .andReturn();
    }

    @Test
    void getAirport() throws Exception {
        when((service.getAirport(any()))).thenReturn(airport);

        mvc.perform(get(apiString + "/" + airport.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(airport.getCode()))
                .andExpect(jsonPath("$.city").value(airport.getCity()))
                .andExpect(jsonPath("$.name").value(airport.getName()))
                .andExpect(jsonPath("$.coordinates").value(airport.getCoordinates()))
                .andExpect(jsonPath("$.timezone").value(airport.getTimezone()))
                .andReturn();
    }

    @Test
    void createAirport() throws Exception {
        when(service.createAirport(any())).thenReturn(airport);
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(airport.getCode()))
                .andExpect(jsonPath("$.city").value(airport.getCity()))
                .andExpect(jsonPath("$.name").value(airport.getName()))
                .andExpect(jsonPath("$.coordinates").value(airport.getCoordinates()))
                .andExpect(jsonPath("$.timezone").value(airport.getTimezone()))
                .andReturn();
    }

    @Test
    void updateAirport() throws Exception {
        when(service.updateAirport(any())).thenReturn(airport);
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(put(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(airport.getCode()))
                .andExpect(jsonPath("$.city").value(airport.getCity()))
                .andExpect(jsonPath("$.name").value(airport.getName()))
                .andExpect(jsonPath("$.coordinates").value(airport.getCoordinates()))
                .andExpect(jsonPath("$.timezone").value(airport.getTimezone()))
                .andReturn();
    }

    @Test
    void deleteAirport() throws Exception {
        mvc.perform(delete(apiString + "/" + airport.getCode()))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteAirport(airport.getCode());
    }
}