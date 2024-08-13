package ru.katiafill.bookings.shared.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final JwtAuthorizationProperties properties;

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return getRoles(source)
                .stream()
                .map(role -> new SimpleGrantedAuthority(properties.getPrefixRole() + role))
                .collect(Collectors.toSet());
    }

    private List<String> getRoles(Jwt source) {
        Map<String, Map<String, List<String>>> resourceAccess = getResourceAccess(source);
        Map<String, List<String>> bookings = getBookings(resourceAccess);
        List<String> roles = bookings.get(properties.getClaimRoles());
        return roles != null ? roles : List.of();
    }

    private Map<String, List<String>> getBookings(Map<String, Map<String, List<String>>> resourceAccess) {
        Map<String, List<String>> bookings = resourceAccess.get(properties.getClaimRealmName());
        return bookings != null ? bookings : Map.of();
    }

    private Map<String, Map<String, List<String>>> getResourceAccess(Jwt source) {
        Map<String, Map<String, List<String>>> resourceAccess = source.getClaim(properties.getClaimResourceAccess());
        return resourceAccess != null ? resourceAccess : Map.of();
    }
}
