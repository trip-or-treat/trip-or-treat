package com.triportreat.backend.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class KakaoUserInfoResponseDto {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter @Setter
    public static class KakaoAccount {

        private KakaoProfile profile;

        private String email;

        public KakaoAccount() {
            this.profile = new KakaoProfile(null, null, null);
        }

    }

    @Getter @Setter
    @Builder
    public static class KakaoProfile {

        private String nickname;

        @JsonProperty("profile_image_url")
        private String imageOrigin;

        @JsonProperty("thumbnail_image_url")
        private String imageThumbnail;
    }
}
