package com.triportreat.backend.auth.controller;

import static com.triportreat.backend.common.response.SuccessMessage.LOGIN_SUCCESS;

import com.triportreat.backend.auth.service.AuthService;
import com.triportreat.backend.common.response.ResponseResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<?> loginByKakao(String code,
                                          HttpServletResponse response) {
        authService.loginByKakao(code, response);
        return ResponseEntity.ok().body(ResponseResult.success(LOGIN_SUCCESS.getMessage(), null));
    }
}
