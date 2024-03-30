package com.triportreat.backend.auth.filter;

import static com.triportreat.backend.common.response.FailMessage.TOKEN_EXPIRED;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_INVALID;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_MALFORMED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtExceptionFilterTest {

    @InjectMocks
    private JwtExceptionFilter jwtExceptionFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        jwtExceptionFilter = new JwtExceptionFilter(objectMapper);
    }

    @Test
    @DisplayName("SignatureException 예외 처리")
    void jwtExceptionFilter_SignatureException() throws Exception {
        // given
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        doThrow(new SignatureException(TOKEN_INVALID.getMessage())).when(chain).doFilter(request, response);

        // when
        jwtExceptionFilter.doFilter(request, response, chain);

        // then
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setStatus(200);

        String expectedJsonBody = """
                {"result":false,"message":"토큰이 유효하지 않습니다!","status":401,"data":null}""";
        assertThat(stringWriter.toString()).isEqualTo(expectedJsonBody);
    }

    @Test
    @DisplayName("MalformedJwtException 예외 처리")
    void jwtExceptionFilter_MalformedJwtException() throws Exception {
        // given
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        doThrow(new MalformedJwtException(TOKEN_MALFORMED.getMessage())).when(chain).doFilter(request, response);

        // when
        jwtExceptionFilter.doFilter(request, response, chain);

        // then
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setStatus(200);

        String expectedJsonBody = """
                {"result":false,"message":"토큰이 변조되었습니다!","status":401,"data":null}""";
        assertThat(stringWriter.toString()).isEqualTo(expectedJsonBody);
    }

    @Test
    @DisplayName("ExpiredJwtException 예외 처리")
    void jwtExceptionFilter_ExpiredJwtException() throws Exception {
        // given
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        doThrow(new MalformedJwtException(TOKEN_EXPIRED.getMessage())).when(chain).doFilter(request, response);

        // when
        jwtExceptionFilter.doFilter(request, response, chain);

        // then
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setStatus(200);

        String expectedJsonBody = """
                {"result":false,"message":"토큰이 만료되었습니다!","status":401,"data":null}""";
        assertThat(stringWriter.toString()).isEqualTo(expectedJsonBody);
    }
}