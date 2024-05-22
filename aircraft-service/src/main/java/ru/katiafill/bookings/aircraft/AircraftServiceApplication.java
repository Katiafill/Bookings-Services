package ru.katiafill.bookings.aircraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class AircraftServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AircraftServiceApplication.class, args);
    }
}
