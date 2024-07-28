package ru.katiafill.bookings.aircraft.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

@Slf4j
public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        Map<String, Map<String, List<String>>> resourceAccess = source.getClaim("resource_access");

        if (resourceAccess != null && !resourceAccess.isEmpty()) {
            resourceAccess.forEach((resource, resourceClaims) -> {
                if (resource.equals("bookings")) {
                    resourceClaims.get("roles").forEach(role -> {
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    });
                }
            });
        }

        log.info("Authorities: {}", mappedAuthorities);
        return mappedAuthorities;
    }
}
