package ru.katiafill.bookings.aircraft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.katiafill.bookings.aircraft.exception.ResourceNotFoundException;
import ru.katiafill.bookings.aircraft.model.Aircraft;
import ru.katiafill.bookings.aircraft.model.LocalizedString;
import ru.katiafill.bookings.aircraft.service.AircraftService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AircraftController.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AircraftControllerTest {

    private static final String apiString = "/api/aircraft";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AircraftService service;

    private Aircraft aircraft;

    @BeforeEach
    void setUp() {
        aircraft = Aircraft.builder()
                .code("SMP")
                .model(new LocalizedString("Sample", "Пример"))
                .range(1000)
                .build();
    }

    @Test
    void getAircrafts() throws Exception {
        when(service.getAircrafts()).thenReturn(List.of(aircraft));

        mvc.perform(get(apiString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code").value(aircraft.getCode()))
                .andExpect(jsonPath("$[0].model").value(aircraft.getModel()))
                .andExpect(jsonPath("$[0].range").value(aircraft.getRange()))
                .andReturn();
    }

    @Test
    void getAircraftById() throws Exception {
        when(service.getAircraft(aircraft.getCode(), false)).thenReturn(aircraft);

        mvc.perform(get(apiString + "/" + aircraft.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(aircraft.getCode()))
                .andExpect(jsonPath("$.model").value(aircraft.getModel()))
                .andExpect(jsonPath("$.range").value(aircraft.getRange()))
                .andReturn();
    }

    @Test
    void addAircraft() throws Exception {
        when(service.createAircraft(any())).thenReturn(aircraft);
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aircraft)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(aircraft.getCode()))
                .andExpect(jsonPath("$.model").value(aircraft.getModel()))
                .andExpect(jsonPath("$.range").value(aircraft.getRange()))
                .andReturn();
    }

    @Test
    void deleteAircraft() throws Exception {
        mvc.perform(delete(apiString + "/" + aircraft.getCode()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNoAircraft() throws Exception {
        doThrow(new ResourceNotFoundException("Delete Error"))
                .when(service)
                .deleteAircraft(aircraft.getCode());

        mvc.perform(delete(apiString + "/" + aircraft.getCode()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Delete Error"));

    }

    @Test
    void updateAircraft() throws Exception {
        when(service.updateAircraft(any())).thenReturn(aircraft);
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(put(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aircraft)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(aircraft.getCode()))
                .andExpect(jsonPath("$.model").value(aircraft.getModel()))
                .andExpect(jsonPath("$.range").value(aircraft.getRange()))
                .andReturn();
    }
}