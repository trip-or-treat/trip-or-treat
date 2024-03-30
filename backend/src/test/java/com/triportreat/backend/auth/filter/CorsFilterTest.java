package com.triportreat.backend.auth.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CorsFilterTest {

    @InjectMocks
    private CorsFilter corsFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    public void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        corsFilter = new CorsFilter();
        corsFilter.init(null);
    }

    @ParameterizedTest(name = "{index}:{0}")
    @ValueSource(strings = {
            "http://localhost:3000",
            "https://localhost:3000",
            "http://localhost:8080",
            "https://localhost:8080",
            "http://triportreat.site",
            "https://triportreat.site",
            "http://www.triportreat.site",
            "https://www.triportreat.site"})
    @DisplayName("성공")
    void corsFilter_Success(String origin) throws Exception {
        //given
        when(request.getHeader(ORIGIN)).thenReturn(origin);

        //when
        corsFilter.doFilter(request, response, chain);

        //then
        verify(response).setStatus(OK.value());
        verify(response).setContentType(APPLICATION_JSON_VALUE);
        verify(response).setCharacterEncoding("UTF-8");

        verify(response).setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        verify(response).setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization");
        verify(response).setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, AUTHORIZATION);
        verify(response).setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        verify(response).setHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD");
        verify(response).setHeader(ACCESS_CONTROL_MAX_AGE, "3600");

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("실패 - 허용되지 않은 Origin은 Cors 설정을 적용하지 않음")
    void corsFilter_Fail() throws Exception {
        //given
        String origin = "https://abc.com";

        when(request.getHeader(ORIGIN)).thenReturn(origin);

        //when
        corsFilter.doFilter(request, response, chain);

        //then
        assertThat(response.getStatus()).isNotEqualTo(OK.value());
        assertThat(response.getContentType()).isNull();
        assertThat(response.getCharacterEncoding()).isNull();

        assertThat(response.getHeader(ACCESS_CONTROL_ALLOW_ORIGIN)).isNull();
        assertThat(response.getHeader(ACCESS_CONTROL_ALLOW_HEADERS)).isNull();
        assertThat(response.getHeader(ACCESS_CONTROL_EXPOSE_HEADERS)).isNull();
        assertThat(response.getHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS)).isNull();
        assertThat(response.getHeader(ACCESS_CONTROL_ALLOW_METHODS)).isNull();
        assertThat(response.getHeader(ACCESS_CONTROL_MAX_AGE)).isNull();

        verify(chain, times(1)).doFilter(request, response);
    }
}
