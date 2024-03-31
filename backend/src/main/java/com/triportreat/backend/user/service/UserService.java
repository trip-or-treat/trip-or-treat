package com.triportreat.backend.user.service;

import com.triportreat.backend.user.domain.UserNicknameRequestDto;

public interface UserService {

    void updateUserNickname(Long userId, UserNicknameRequestDto userNicknameRequestDto);
}
