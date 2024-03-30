package com.triportreat.backend.auth.filter;

import static com.triportreat.backend.common.response.FailMessage.TOKEN_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.triportreat.backend.auth.utils.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Test
    @DisplayName("preflight 요청")
    void doFilter_preflight() throws Exception {
        // given
        String method = "OPTIONS";

        when(request.getMethod()).thenReturn(method);

        // when
        jwtAuthenticationFilter.doFilter(request, response, chain);

        // then
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("whitelist에 포함된 요청")
    void doFilter_whitelist() throws Exception {
        // given
        String uri = "/places";

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn(uri);

        // when
        jwtAuthenticationFilter.doFilter(request, response, chain);

        // then
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("액세스토큰이 유효한 경우")
    void doFilter_validAccessToken() throws Exception {
        // given
        String uri = "/plans/1";
        String accessToken = "validAccessToken";

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn(uri);
        when(jwtProvider.extractAccessToken(request)).thenReturn(accessToken);
        when(jwtProvider.isValid(accessToken)).thenReturn(true);

        //when
        jwtAuthenticationFilter.doFilter(request, response, chain);

        //then
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("액세스토큰이 만료되고, 리프레시토큰이 유효한 경우")
    void doFilter_expiredAccessToken() throws Exception {
        // given
        String uri = "/plans/1";
        String accessToken = "expiredAccessToken";
        String refreshToken = "validRefreshToken";

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn(uri);
        when(jwtProvider.extractAccessToken(request)).thenReturn(accessToken);
        when(jwtProvider.isValid(accessToken)).thenThrow(
                new ExpiredJwtException(null, null, TOKEN_EXPIRED.getMessage()));
        when(jwtProvider.extractRefreshToken(request)).thenReturn(refreshToken);
        when(jwtProvider.isValid(refreshToken)).thenReturn(true);

        // when
        jwtAuthenticationFilter.doFilter(request, response, chain);

        // then
        HttpServletRequest newTokenReq = verify(jwtProvider).reIssueAccessToken(refreshToken, response, request);
        verify(chain, times(1)).doFilter(newTokenReq, response);
    }

    @Test
    @DisplayName("액세스토큰이 만료되고, 리프레시토큰도 만료된 경우")
    void doFilter_expiredRefreshToken() throws Exception {
        // given
        String uri = "/plans/1";
        String accessToken = "expiredAccessToken";
        String refreshToken = "expiredRefreshToken";

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn(uri);
        when(jwtProvider.extractAccessToken(request)).thenReturn(accessToken);
        when(jwtProvider.isValid(accessToken)).thenThrow(
                new ExpiredJwtException(null, null, TOKEN_EXPIRED.getMessage()));
        when(jwtProvider.extractRefreshToken(request)).thenReturn(refreshToken);
        when(jwtProvider.isValid(refreshToken)).thenThrow(
                new ExpiredJwtException(null, null, TOKEN_EXPIRED.getMessage()));

        // when
        Exception exception = catchException(() -> jwtAuthenticationFilter.doFilter(request, response, chain));

        // then
        verify(chain, never()).doFilter(request, response);
        assertThat(exception).isInstanceOf(ExpiredJwtException.class);
        assertThat(exception.getMessage()).isEqualTo(TOKEN_EXPIRED.getMessage());
    }
}