package com.triportreat.backend.auth.controller;

import static com.triportreat.backend.common.response.SuccessMessage.LOGIN_SUCCESS;

import com.triportreat.backend.auth.domain.KakaoLoginRequestDto;
import com.triportreat.backend.auth.service.AuthService;
import com.triportreat.backend.common.response.ResponseResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginByKakao(@RequestBody KakaoLoginRequestDto loginRequestInfo,
                                          HttpServletResponse response) {
        authService.loginByKakao(loginRequestInfo, response);
        return ResponseEntity.ok().body(ResponseResult.success(LOGIN_SUCCESS.getMessage(), null));
    }
}
