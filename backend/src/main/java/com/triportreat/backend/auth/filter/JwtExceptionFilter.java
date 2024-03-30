package com.triportreat.backend.auth.filter;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class JwtExceptionFilter implements Filter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        log.info("JWT 예외 처리 필터 실행");
        try {
            log.info("JWT 예외 처리 필터 실행중, 다음 필터로 이동");
            chain.doFilter(req, res);
        } catch (JwtException e) {
            log.info("JWT 예외 발생: {}", e.getMessage());
            jwtExceptionHandler((HttpServletResponse) res, e);
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, JwtException e) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(OK.value());

        ResponseResult result = ResponseResult.fail(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
        String jsonResponse = objectMapper.writeValueAsString(result);
        response.getWriter().write(jsonResponse);
        log.info("JWT 예외 처리 후 응답 반환");
    }
}
