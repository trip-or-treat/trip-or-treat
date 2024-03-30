package com.triportreat.backend.auth.filter;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class CorsFilter implements Filter {

    private static final HashSet<String> allowedOrigin = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        allowedOrigin.add("http://localhost:3000");
        allowedOrigin.add("https://localhost:3000");
        allowedOrigin.add("http://localhost:8080");
        allowedOrigin.add("https://localhost:8080");
        allowedOrigin.add("http://triportreat.site");
        allowedOrigin.add("https://triportreat.site");
        allowedOrigin.add("http://www.triportreat.site");
        allowedOrigin.add("https://www.triportreat.site");
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {
        log.info("CORS 필터 실행");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        log.info("요청 헤더 origin: {}", request.getHeader(ORIGIN));
        String origin = request.getHeader(ORIGIN);
        if (allowedOrigin.contains(origin)) {
            log.info("허용된 origin: {}", origin);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(OK.value());

            response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "*");
            response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, AUTHORIZATION);
            response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD");
            response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
        }
        log.info("CORS 필터 종료, 다음 필터로 이동");
        chain.doFilter(request, response);
    }
}
