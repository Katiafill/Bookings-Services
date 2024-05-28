package ru.katiafill.bookings.aircraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RefreshScope
@ComponentScan(basePackages = {
        "ru.katiafill.bookings.aircraft.*",
        "ru.katiafill.bookings.shared.*"
})
public class AircraftServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AircraftServiceApplication.class, args);
    }
}
