package com.triportreat.backend.user.controller;

import com.triportreat.backend.auth.utils.Auth;
import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.user.domain.UserNicknameRequestDto;
import com.triportreat.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.triportreat.backend.common.response.SuccessMessage.PUT_SUCCESS;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/user/nickname")
    public ResponseEntity<?> updateUserNickname(@Auth Long userId, @Valid @RequestBody UserNicknameRequestDto userNicknameRequestDto) {
        userService.updateUserNickname(userId, userNicknameRequestDto);
        return ResponseEntity.ok().body(ResponseResult.success(PUT_SUCCESS.getMessage(), null));
    }
}
