package ru.katiafill.bookings.flight.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListenerClient {

    @KafkaListener(topics = "ratings", groupId = "bookings-flights-group")
    void listener(String data) {
        log.info("Receive message from kafka: {}", data);
    }
}
