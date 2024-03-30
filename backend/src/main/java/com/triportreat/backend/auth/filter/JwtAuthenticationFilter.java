package com.triportreat.backend.auth.filter;

import static org.springframework.util.PatternMatchUtils.simpleMatch;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

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
@Order(3)
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private static final String[] whitelist = {
            "/",
            "/places", "/places/*",
            "/regions", "/regions/*",
            "/plans/share/*",
            "/login"
    };

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        log.info("JWT 인증 필터 실행");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        log.info("요청 메서드: {}", request.getMethod());
        if (request.getMethod().equals(OPTIONS.name())) {
            log.info("Preflight 요청은 허용, 필터 체인 종료, Servlet으로 이동");
            chain.doFilter(req, res);
            return;
        }

        log.info("요청 URI: {}", request.getRequestURI());
        if (isInWhitelist(request.getRequestURI())) {
            log.info("허용된 URI, 필터 체인 종료, Servlet으로 이동");
            chain.doFilter(req, res);
            return;
        }

        log.info("토큰 검증 시작");
        try {
            String accessToken = jwtProvider.extractAccessToken(request);
            log.info("액세스 토큰 추출: {}", accessToken);
            if (jwtProvider.isValid(accessToken)) {
                log.info("액세스 토큰 유효, 필터 체인 종료, Servlet으로 이동");
                chain.doFilter(req, res);
            }
        } catch (ExpiredJwtException e) {
            log.info("액세스 토큰 만료, 리프레시 토큰 검증 시작");
            String refreshToken = jwtProvider.extractRefreshToken(request);
            log.info("리프레시 토큰 추출: {}", refreshToken);
            if (jwtProvider.isValid(refreshToken)) {
                log.info("리프레시 토큰 유효, 액세스 토큰 재발급");
                HttpServletRequest newTokenReq = jwtProvider.reIssueAccessToken(refreshToken, response, request);
                log.info("액세스 토큰 재발급 완료, 필터 체인 종료, Servlet으로 이동");
                chain.doFilter(newTokenReq, res);
            }
        }
    }

    public boolean isInWhitelist(String requestURI) {
        return simpleMatch(whitelist, requestURI);
    }
}
