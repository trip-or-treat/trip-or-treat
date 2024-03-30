package com.triportreat.backend.auth.utils;

import static com.triportreat.backend.common.response.FailMessage.TOKEN_EXPIRED;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_INVALID;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_MALFORMED;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_NOT_FOUND;
import static org.springframework.boot.web.server.Cookie.SameSite.NONE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.access-token.expiration-time}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh-token.expiration-time}")
    private Long refreshTokenExpirationTime;

    private final String AUTH_TYPE = "Bearer ";
    private final String REFRESH_TOKEN_NAME = "refreshToken";
    private final String USER_INFO = "userInfo";

    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.jwtParser = Jwts.parserBuilder().setSigningKey(this.secretKey).build();
    }

    public void issueAccessToken(Long userInfo, HttpServletResponse response) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpirationTime);

        String accessToken = Jwts.builder()
                .claim(USER_INFO, userInfo)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        setAccessToken(response, accessToken);
    }

    public void issueRefreshToken(Long userInfo, HttpServletResponse response) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenExpirationTime);

        String refreshToken = Jwts.builder()
                .claim(USER_INFO, userInfo)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        setRefreshToken(response, refreshToken);
    }

    public HttpServletRequest reIssueAccessToken(String refreshToken, HttpServletResponse response, HttpServletRequest request) {
        issueAccessToken(getUserInfo(refreshToken), response);

        String issuedAccessToken = response.getHeader(AUTHORIZATION);
        return new CustomHttpServletRequestWrapper(request, issuedAccessToken);
    }

    public void setAccessToken(HttpServletResponse response, String accessToken) {
        response.addHeader(AUTHORIZATION, AUTH_TYPE + accessToken);
    }

    public void setRefreshToken(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite(NONE.name())
                .maxAge(3600 * 24)
                .build();

        response.addHeader(SET_COOKIE, cookie.toString());
    }

    public String extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
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
        if (token == null) {
            throw new JwtException(TOKEN_NOT_FOUND.getMessage());
        }

        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new SignatureException(TOKEN_INVALID.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(TOKEN_MALFORMED.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), TOKEN_EXPIRED.getMessage());
        }
    }

    public Long getUserInfo(String token) {
        return jwtParser.parseClaimsJws(token)
                .getBody()
                .get(USER_INFO, Long.class);
    }
}
