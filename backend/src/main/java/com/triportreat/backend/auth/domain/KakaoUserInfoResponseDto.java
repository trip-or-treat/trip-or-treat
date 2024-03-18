package com.triportreat.backend.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class KakaoUserInfoResponseDto {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class KakaoAccount {

        private KakaoProfile profile;

        private String email;
    }

    @Getter
    public static class KakaoProfile {

        private String nickname;

        @JsonProperty("profile_image_url")
        private String imageOrigin;

        @JsonProperty("thumbnail_image_url")
        private String imageThumbnail;
    }
}
