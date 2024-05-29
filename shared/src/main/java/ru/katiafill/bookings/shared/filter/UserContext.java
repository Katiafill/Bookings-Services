package ru.katiafill.bookings.shared.filter;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserContext {
    public static final String CORRELATION_ID = "x-correlation-id";
    public static final String AUTH_TOKEN     = "x-auth-token";
    public static final String USER_ID        = "x-user-id";

    private String correlationId;
    private String authToken;
    private String userId;
}
