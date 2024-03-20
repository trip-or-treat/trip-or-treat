package com.triportreat.backend.auth.filter;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triportreat.backend.common.response.ResponseResult;
import io.jsonwebtoken.JwtException;
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
public class JwtExceptionFilter implements Filter {

    private static HashSet<String> allowedOrigin = new HashSet<>();

    private final ObjectMapper objectMapper;

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
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        log.info("검증 예외 필터 동작");

        try {
            chain.doFilter(request, response);
        } catch (JwtException e) {
            log.info("예외 필터에서 예외 캐치");
            jwtExceptionHandler((HttpServletRequest) request, (HttpServletResponse) response, e);
        }
    }

    public void jwtExceptionHandler(HttpServletRequest request, HttpServletResponse response, JwtException e) {
        String origin = request.getHeader("Origin");
        if (allowedOrigin.contains(origin)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(OK.value());

            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
        }
        
        try {
            ResponseResult result = ResponseResult.fail(e.getMessage(), UNAUTHORIZED, null);
            String jsonResponse = objectMapper.writeValueAsString(result);
            response.getWriter().write(jsonResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
