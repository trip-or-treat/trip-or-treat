package com.triportreat.backend.auth.filter;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triportreat.backend.common.response.ResponseResult;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class JwtExceptionFilter implements Filter {

    private final ObjectMapper objectMapper;

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
            jwtExceptionHandler((HttpServletResponse) response, e);
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, JwtException e) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(OK.value());
        response.setHeader("Access-Control-Allow-Origin",
                "http://localhost:3000, "
                        + "https://localhost:3000, "
                        + "http://localhost:8080, "
                        + "https://localhost:8080, "
                        + "http://triportreat.site, "
                        + "https://triportreat.site, "
                        + "http://www.triportreat.site, "
                        + "https://www.triportreat.site");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            ResponseResult result = ResponseResult.fail(e.getMessage(), UNAUTHORIZED, null);
            String jsonResponse = objectMapper.writeValueAsString(result);
            response.getWriter().write(jsonResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
