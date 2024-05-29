package ru.katiafill.bookings.shared.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        UserContext userContext = UserContextHolder.getContext();
        userContext.setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID) );
        userContext.setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        userContext.setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));

        chain.doFilter(request, response);
    }
}
