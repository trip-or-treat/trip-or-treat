package com.triportreat.backend.auth.service;

import com.triportreat.backend.auth.domain.KakaoTokenResponseDto;
import com.triportreat.backend.auth.domain.KakaoUserInfoResponseDto;
import com.triportreat.backend.auth.domain.KakaoUserInfoResponseDto.KakaoAccount;
import com.triportreat.backend.auth.utils.JwtProvider;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void loginByKakao(String code, HttpServletRequest request, HttpServletResponse response) {
        String kakaoAccessToken = getKakaoAccessToken(code, request);

        KakaoAccount userInfo = getUserInfo(kakaoAccessToken);

        User user = getUser(userInfo);

        jwtProvider.issueAccessToken(user.getId(), response);
        jwtProvider.issueRefreshToken(user.getId(), response);
    }

    private User getUser(KakaoAccount userInfo) {
        if (!isAlreadyUser(userInfo)) {
            if (userInfo.getProfile().getNickname() == null) {
                setRandomNickname(userInfo);
            }
            return userRepository.save(User.toEntity(userInfo));
        }
        return userRepository.findByEmail(userInfo.getEmail());
    }

    private void setRandomNickname(KakaoAccount userInfo) {
        String randomNickname;
        do {
            randomNickname = "사용자 " + RandomStringUtils.randomAlphanumeric(4);
        } while (userRepository.existsByNickname(randomNickname));

        userInfo.getProfile().setNickname(randomNickname);
    }

    private boolean isAlreadyUser(KakaoAccount userInfo) {
        return userRepository.existsByEmail(userInfo.getEmail());
    }

    private KakaoAccount getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        KakaoUserInfoResponseDto response = restTemplate
                .postForObject(userInfoUri, new HttpEntity<>(headers), KakaoUserInfoResponseDto.class);

        return response.getKakaoAccount();
    }

    private String getKakaoAccessToken(String code, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", request.getHeader(HttpHeaders.ORIGIN) + redirectUri);
        params.add("code", code);

        KakaoTokenResponseDto response = restTemplate
                .postForObject(tokenUri, new HttpEntity<>(params, headers), KakaoTokenResponseDto.class);

        return response.getAccessToken();
    }
}
