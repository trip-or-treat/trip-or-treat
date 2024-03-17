package com.triportreat.backend.auth.filter;

import static org.springframework.util.PatternMatchUtils.simpleMatch;

import com.triportreat.backend.auth.utils.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private static final String[] whitelist = {"/", "/places/*", "/regions/*", "/plans/share/*", "/login"};

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        log.info("검증 필터 동작");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isInWhitelist(httpRequest.getRequestURI())) {
            log.info("화이트리스트 검증");
            chain.doFilter(request, response);
            return;
        }

        try {
            log.info("액세스 토큰 검증");
            String accessToken = jwtProvider.extractAccessToken(httpRequest);
            if (jwtProvider.isValid(accessToken)) {
                log.info("액세스 토큰 검증완료");
                chain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            log.info("액세스 토큰 만료, 리프레시 토큰 검증");
            String refreshToken = jwtProvider.extractRefreshToken(httpRequest);
            if (jwtProvider.isValid(refreshToken)) {
                log.info("리프레시 토큰 검증완료");
                String newAccessToken = jwtProvider.reIssueAccessToken(refreshToken);
                jwtProvider.setAccessToken(httpResponse, newAccessToken);
                log.info("리프레시 토큰으로 액세스 토큰 재발급");
                chain.doFilter(request, response);
            }
        }
    }

    public boolean isInWhitelist(String requestURI) {
        return simpleMatch(whitelist, requestURI);
    }
}
