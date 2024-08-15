package ru.katiafill.bookings.flight.filter;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.katiafill.bookings.shared.filter.UserContextHolder;

@Configuration
public class AuthFeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", UserContextHolder.getContext().getAuthToken());
        };
    }
}
