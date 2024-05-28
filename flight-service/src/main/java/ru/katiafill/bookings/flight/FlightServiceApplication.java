package ru.katiafill.bookings.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RefreshScope
@EnableFeignClients
@ComponentScan(basePackages = {
        "ru.katiafill.bookings.flight.*",
        "ru.katiafill.bookings.shared.*"
})
public class FlightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightServiceApplication.class, args);
    }

}
