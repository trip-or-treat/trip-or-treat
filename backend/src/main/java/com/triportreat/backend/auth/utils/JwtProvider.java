package com.triportreat.backend.auth.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.access-token.expiration-time}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh-token.expiration-time}")
    private Long refreshTokenExpirationTime;

    private final String AUTH_FIELD = "Authorization";
    private final String AUTH_TYPE = "Bearer ";
    private final String REFRESH_TOKEN_NAME = "refreshToken";
    private final String USER_INFO = "userInfo";

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String issueAccessToken(Long userInfo) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts.builder()
                .claim(USER_INFO, userInfo)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String issueRefreshToken(Long userInfo) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenExpirationTime);

        return Jwts.builder()
                .claim(USER_INFO, userInfo)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String reIssueAccessToken(String refreshToken) {
        return issueAccessToken(getUserInfo(refreshToken));
    }

    public void setAccessToken(HttpServletResponse response, String accessToken) {
        response.addHeader(AUTH_FIELD, AUTH_TYPE + accessToken);
    }

    public void setAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(3600 * 24)
                .build();

        response.addHeader(AUTH_FIELD, AUTH_TYPE + accessToken);
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public String extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTH_FIELD))
                .filter(authHeader -> authHeader.startsWith(AUTH_TYPE))
                .map(authHeader -> authHeader.replace(AUTH_TYPE, ""))
                .orElse(null);
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> REFRESH_TOKEN_NAME.equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst())
                        .orElse(null);
    }

    public boolean isValid(String token) {
        try {
            if (token == null) {
                log.info("토큰이 존재하지 않습니다.");
                throw new JwtException("토큰이 존재하지 않습니다.");
            }

            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (SignatureException e) {
            log.info("토큰이 유효하지 않습니다.");
            throw new SignatureException("토큰이 유효하지 않습니다.");
        } catch (MalformedJwtException e) {
            log.info("토큰이 올바르지 않습니다.");
            throw new MalformedJwtException("토큰이 올바르지 않습니다.");
        } catch (ExpiredJwtException e) {
            log.info("토큰이 만료되었습니다.");
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "토큰이 만료되었습니다.");
        }
    }

    public Long getUserInfo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(USER_INFO, Long.class);
    }
}
