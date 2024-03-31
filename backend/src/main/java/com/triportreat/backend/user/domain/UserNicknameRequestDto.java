package com.triportreat.backend.user.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNicknameRequestDto {

    @NotNull(message = "닉네임은 필수 입력값입니다.")
    private String nickname;
}
