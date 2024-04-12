package com.triportreat.backend.user.service;

import com.triportreat.backend.user.domain.UserInfoResponseDto;
import com.triportreat.backend.user.domain.UserNicknameRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void updateUserNickname(Long userId, UserNicknameRequestDto userNicknameRequestDto);

    String updateUserProfile(MultipartFile file, Long userId);

    UserInfoResponseDto getUserInfo(Long userId);
}
