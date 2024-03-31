package com.triportreat.backend.auth.controller;

import static com.triportreat.backend.common.response.SuccessMessage.LOGIN_SUCCESS;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.triportreat.backend.auth.filter.JwtAuthenticationFilter;
import com.triportreat.backend.auth.service.AuthService;
import com.triportreat.backend.auth.utils.AuthUserArgumentResolver;
import com.triportreat.backend.common.config.WebConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AuthController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        JwtAuthenticationFilter.class,
                        AuthUserArgumentResolver.class,
                        WebConfig.class}))
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Nested
    @DisplayName("카카오 로그인")
    class LoginByKakao {

        @Test
        @DisplayName("성공")
        void loginByKakao() throws Exception {
            // given
            String code = "testKakaoAuthenticationCode";
            HttpServletRequest request = new MockHttpServletRequest();
            HttpServletResponse response = new MockHttpServletResponse();

            doNothing().when(authService).loginByKakao(code, request, response);

            // when
            // then
            mockMvc.perform(get("/login"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value(LOGIN_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }

}