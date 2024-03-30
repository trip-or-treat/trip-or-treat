package com.triportreat.backend.auth.utils;

import static com.triportreat.backend.common.response.FailMessage.TOKEN_EXPIRED;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_INVALID;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_MALFORMED;
import static com.triportreat.backend.common.response.FailMessage.TOKEN_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    private JwtProvider jwtProvider;

    private String secretKey;

    @BeforeEach
    void setUp() {
        secretKey = "testSecretKeytestSecretKeytestSecretKeytestSecretKeytestSecretKeytestSecretKeytestSecretKey";
        jwtProvider = new JwtProvider(secretKey);

        ReflectionTestUtils.setField(jwtProvider, "accessTokenExpirationTime", 1000L * 60 * 30);
        ReflectionTestUtils.setField(jwtProvider, "refreshTokenExpirationTime", 1000L * 60 * 60 * 24);
    }

    @Test
    @DisplayName("액세스 토큰 발급")
    void issueAccessToken() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        Long userInfo = 13624982L;

        // when
        jwtProvider.issueAccessToken(userInfo, response);

        // then
        assertThat(response.getHeader("Authorization")).isNotNull();
        assertThat(response.getHeader("Authorization").startsWith("Bearer ")).isTrue();
    }

    @Test
    @DisplayName("리프레시 토큰 발급")
    void issueRefreshToken() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        Long userInfo = 13624982L;

        // when
        jwtProvider.issueRefreshToken(userInfo, response);

        // then
        assertThat(response.getHeader("Set-Cookie")).isNotNull();
        assertThat(response.getHeader("Set-Cookie").startsWith("refreshToken=")).isTrue();
    }

    @Test
    @DisplayName("액세스 토큰 재발급")
    void reissueAccessToken() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Long userInfo = 13624982L;

        jwtProvider.issueRefreshToken(userInfo, response);
        String refreshToken = response.getHeader("Set-Cookie").split("=")[1].split(";")[0];

        // when
        jwtProvider.reIssueAccessToken(refreshToken, response, request);

        // then
        assertThat(response.getHeader("Authorization")).isNotNull();
        assertThat(response.getHeader("Authorization").startsWith("Bearer ")).isTrue();
    }

    @Test
    @DisplayName("토큰 유저 정보 추출")
    void getUserInfo() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        Long userInfo = 13624982L;

        jwtProvider.issueAccessToken(userInfo, response);
        String accessToken = response.getHeader("Authorization").split(" ")[1];

        // when
        Long extractedUserInfo = jwtProvider.getUserInfo(accessToken);

        // then
        assertThat(extractedUserInfo).isEqualTo(userInfo);
    }

    @Nested
    @DisplayName("토큰 유효성 검증")
    class IsValid {

        @Test
        @DisplayName("성공")
        void isValid() {
            // given
            MockHttpServletResponse response = new MockHttpServletResponse();
            Long userInfo = 13624982L;

            jwtProvider.issueAccessToken(userInfo, response);
            String accessToken = response.getHeader("Authorization").split(" ")[1];

            // when
            boolean isValid = jwtProvider.isValid(accessToken);

            // then
            assertThat(isValid).isTrue();
        }

        @Test
        @DisplayName("실패 - 토큰이 존재하지 않는 경우")
        void isValid_JwtNotFound() {
            // given
            String accessToken = null;

            // when
            // then
            assertThatThrownBy(() -> jwtProvider.isValid(accessToken))
                    .isInstanceOf(JwtException.class)
                    .hasMessage(TOKEN_NOT_FOUND.getMessage());

        }

        @Test
        @DisplayName("실패 - 토큰이 만료된 경우")
        void isValid_JwtExpired() {
            // given
            MockHttpServletResponse response = new MockHttpServletResponse();
            Long userInfo = 13624982L;

            ReflectionTestUtils.setField(jwtProvider, "accessTokenExpirationTime", 0L);

            jwtProvider.issueAccessToken(userInfo, response);
            String accessToken = response.getHeader("Authorization").split(" ")[1];

            // when
            // then
            assertThatThrownBy(() -> jwtProvider.isValid(accessToken))
                    .isInstanceOf(ExpiredJwtException.class)
                    .hasMessage(TOKEN_EXPIRED.getMessage());
        }

        @Test
        @DisplayName("실패 - 토큰이 변조된 경우")
        void isValid_JwtInvalid() {
            // given
            MockHttpServletResponse response = new MockHttpServletResponse();
            Long userInfo = 13624982L;

            jwtProvider.issueAccessToken(userInfo, response);
            String accessToken = response.getHeader("Authorization").split(" ")[1] + "invalid";

            // when
            // then
            assertThatThrownBy(() -> jwtProvider.isValid(accessToken))
                    .isInstanceOf(SignatureException.class)
                    .hasMessage(TOKEN_INVALID.getMessage());
        }

        @Test
        @DisplayName("실패 - 토큰이 변조된 경우")
        void isValid_JwtMalformed() {
            // given
            MockHttpServletResponse response = new MockHttpServletResponse();
            Long userInfo = 13624982L;

            jwtProvider.issueAccessToken(userInfo, response);
            String accessToken = "malformed" + response.getHeader("Authorization").split(" ")[1];

            // when
            // then
            assertThatThrownBy(() -> jwtProvider.isValid(accessToken))
                    .isInstanceOf(MalformedJwtException.class)
                    .hasMessage(TOKEN_MALFORMED.getMessage());
        }
    }
}