package com.triportreat.backend.user.domain;

import com.triportreat.backend.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserInfoResponseDto {
    private String nickname;
    private String name;
    private String email;
    private String imageThumbnail;

    public UserInfoResponseDto(User user) {
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.email = user.getEmail();
        this.imageThumbnail = user.getImageThumbnail();
    }
}
