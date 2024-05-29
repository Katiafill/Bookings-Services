package ru.katiafill.bookings.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TrackingFilter implements GlobalFilter {

    @Autowired
    private FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        String correlationID = filterUtils.getCorrelationId(requestHeaders);

        if (correlationID != null) {
            log.debug("{} found in tracking filter: {}. ",
                    FilterUtils.CORRELATION_ID, correlationID);
        } else {
            correlationID = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationID);
            log.debug("{} generated in tracking filter: {}.",
                    FilterUtils.CORRELATION_ID, correlationID);
        }

        return chain.filter(exchange);
    }


    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
