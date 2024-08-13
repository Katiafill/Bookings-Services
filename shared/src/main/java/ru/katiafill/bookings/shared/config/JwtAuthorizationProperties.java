package ru.katiafill.bookings.shared.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "jwt")
public class JwtAuthorizationProperties {
    private String claimResourceAccess;
    private String claimRealmName;
    private String claimRoles;
    private String prefixRole;
}
