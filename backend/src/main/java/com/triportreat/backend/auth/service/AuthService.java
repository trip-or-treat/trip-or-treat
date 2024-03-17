package com.triportreat.backend.auth.service;

import com.triportreat.backend.auth.domain.KakaoTokenResponseDto;
import com.triportreat.backend.auth.domain.KakaoUserInfoResponseDto;
import com.triportreat.backend.auth.domain.KakaoUserInfoResponseDto.KakaoAccount;
import com.triportreat.backend.auth.utils.JwtProvider;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    public void loginByKakao(String code, HttpServletResponse response) {
        //카카오 엑세스토큰 발급
        String kakaoAccessToken = getKakaoAccessToken(code);

        //카카오 사용자정보 조회
        KakaoAccount userInfo = getUserInfo(kakaoAccessToken);

        //사용자 정보를 토대로 신규 유저면 저장, 기존 유저면 조회
        User user = getUser(userInfo);

        //해당 유저에 대해 JWT 토큰 발급
        String accessToken = jwtProvider.issueAccessToken(user.getId());
        String refreshToken = jwtProvider.issueRefreshToken(user.getId());

        //응답 헤더와 쿠키에 토큰 저장
        jwtProvider.setAccessAndRefreshToken(response, accessToken, refreshToken);
    }

    public User getUser(KakaoAccount userInfo) {
        if (!isAlreadyUser(userInfo)) {
            return userRepository.save(User.toEntity(userInfo));
        }
        return userRepository.findByEmail(userInfo.getEmail());
    }

    public boolean isAlreadyUser(KakaoAccount userInfo) {
        return userRepository.existsByEmail(userInfo.getEmail());
    }

    public KakaoAccount getUserInfo(String kakaoAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(kakaoAccessToken);

        KakaoUserInfoResponseDto response = restTemplate
                .postForObject(userInfoUri, new HttpEntity<>(headers), KakaoUserInfoResponseDto.class);

        return response.getKakaoAccount();
    }

    public String getKakaoAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        KakaoTokenResponseDto response = restTemplate
                .postForObject(tokenUri, new HttpEntity<>(params, headers), KakaoTokenResponseDto.class);

        return response.getAccessToken();
    }
}
