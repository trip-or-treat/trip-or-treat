package com.triportreat.backend.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.triportreat.backend.auth.domain.KakaoTokenResponseDto;
import com.triportreat.backend.auth.domain.KakaoUserInfoResponseDto;
import com.triportreat.backend.auth.utils.JwtProvider;
import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest extends DummyObject {

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private HttpServletResponse response;

    @Nested
    @DisplayName("카카오 로그인")
    class LoginByKakao {

        @Test
        @DisplayName("성공 - 이미 가입된 유저")
        void loginByKakao_success() {
            // given
            String code = "kakaoAuthenticationCode";

            KakaoTokenResponseDto mockTokenDto = createMockKakaoTokenResponse();
            KakaoUserInfoResponseDto mockUserInfoDto = createMockKakaoUserInfo();

            User user = createMockUser(1L, "testUser");

            given(restTemplate.postForObject(eq(tokenUri), any(), eq(KakaoTokenResponseDto.class)))
                    .willReturn(mockTokenDto);
            given(restTemplate.postForObject(eq(userInfoUri), any(), eq(KakaoUserInfoResponseDto.class)))
                    .willReturn(mockUserInfoDto);
            when(userRepository.existsByEmail(mockUserInfoDto.getKakaoAccount().getEmail())).thenReturn(true);
            when(userRepository.findByEmail(mockUserInfoDto.getKakaoAccount().getEmail())).thenReturn(user);
            doNothing().when(jwtProvider).issueAccessToken(anyLong(), eq(response));
            doNothing().when(jwtProvider).issueRefreshToken(anyLong(), eq(response));

            // when
            authService.loginByKakao(code, response);

            // then
            verify(jwtProvider, times(1)).issueAccessToken(eq(1L), eq(response));
            verify(jwtProvider, times(1)).issueRefreshToken(eq(1L), eq(response));
        }

        @Test
        @DisplayName("성공 - 신규 유저")
        void loginByKakao_success_newUser() {
            // given
            String code = "kakaoAuthenticationCode";

            KakaoTokenResponseDto mockTokenDto = createMockKakaoTokenResponse();
            KakaoUserInfoResponseDto mockUserInfoDto = createMockKakaoUserInfo();
            User mockUser = createMockUser(1L, null);

            given(restTemplate.postForObject(eq(tokenUri), any(), eq(KakaoTokenResponseDto.class)))
                    .willReturn(mockTokenDto);
            given(restTemplate.postForObject(eq(userInfoUri), any(), eq(KakaoUserInfoResponseDto.class)))
                    .willReturn(mockUserInfoDto);
            when(userRepository.existsByEmail(mockUserInfoDto.getKakaoAccount().getEmail())).thenReturn(false);
            when(userRepository.existsByNickname(any())).thenReturn(false);
            when(userRepository.save(any(User.class))).thenReturn(mockUser);
            doNothing().when(jwtProvider).issueAccessToken(anyLong(), eq(response));
            doNothing().when(jwtProvider).issueRefreshToken(anyLong(), eq(response));

            // when
            authService.loginByKakao(code, response);

            // then
            assertThat(mockUserInfoDto.getKakaoAccount().getProfile().getNickname()).isNotNull();
            assertThat(mockUserInfoDto.getKakaoAccount().getProfile().getNickname().startsWith("사용자 ")).isTrue();
            verify(jwtProvider, times(1)).issueAccessToken(eq(1L), eq(response));
            verify(jwtProvider, times(1)).issueRefreshToken(eq(1L), eq(response));
        }
    }

}