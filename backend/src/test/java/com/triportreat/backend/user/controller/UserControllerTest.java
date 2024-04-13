package com.triportreat.backend.user.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.triportreat.backend.auth.filter.JwtAuthenticationFilter;
import com.triportreat.backend.auth.filter.JwtExceptionFilter;
import com.triportreat.backend.auth.utils.AuthUserArgumentResolver;
import com.triportreat.backend.common.config.WebConfig;
import com.triportreat.backend.place.error.handler.exception.UserNotFoundException;
import com.triportreat.backend.user.domain.UserInfoResponseDto;
import com.triportreat.backend.user.domain.UserNicknameRequestDto;
import com.triportreat.backend.user.error.exception.UserNicknameExistException;
import com.triportreat.backend.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.triportreat.backend.common.response.FailMessage.*;
import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.PUT_SUCCESS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtExceptionFilter.class,
                        JwtAuthenticationFilter.class,
                        AuthUserArgumentResolver.class,
                        WebConfig.class}))
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("사용자 닉네임 변경")
    class PutUserNickname {

        @Test
        @DisplayName("성공")
        void updateUserNickname() throws Exception {

            //given
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname("nickname").build();

            //when & then
            mockMvc.perform(put("/user/nickname")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userNicknameRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value(PUT_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data").isEmpty());

            verify(userService).updateUserNickname(any(), any(UserNicknameRequestDto.class));
        }

        @Test
        @DisplayName("실패 - 사용자 정보가 존재하지 않을 때 예외 발생")
        void updateUserNickname_UserNotFoundException() throws Exception {

            //given
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname("nickname").build();
            doThrow(UserNotFoundException.class).when(userService).updateUserNickname(any(), any(UserNicknameRequestDto.class));

            //when & then
            mockMvc.perform(put("/user/nickname")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userNicknameRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(USER_NOT_FOUND.getMessage()))
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.data").isEmpty());

        }

        @Test
        @DisplayName("실패 - 이미 존재하는 닉네임일 때 예외 발생")
        void updateUserNickname_UserNicknameExistException() throws Exception {

            //given
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname("nickname").build();
            doThrow(UserNicknameExistException.class).when(userService).updateUserNickname(any(), any(UserNicknameRequestDto.class));

            //when & then
            mockMvc.perform(put("/user/nickname")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userNicknameRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(USER_NICKNAME_EXIST.getMessage()))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 닉네임이 현재 닉네임과 같을 때 업데이트를 수행하지 않음")
        void updateNickname_NochangeIfSameNickname() throws Exception {

            //given
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname("nickname").build();

            //when & then
            mockMvc.perform(put("/user/nickname")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userNicknameRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value(PUT_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 유효성 검증 실패")
        void updateNickname_ValidationFail() throws Exception {

            //given
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname(null).build();

            //when & then
            mockMvc.perform(put("/user/nickname")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userNicknameRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(VALIDATION_FAILED.getMessage()))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data.nickname").value("닉네임은 필수 입력값입니다."));
        }
    }

    @Nested
    @DisplayName("회원정보조회")
    class GetUserInfo {

        @Test
        @DisplayName("성공")
        void getUserInfo_Success() throws Exception {
            // given
            UserInfoResponseDto userInfo = UserInfoResponseDto.builder()
                    .name("user1")
                    .email("user1@gmail.com")
                    .nickname("user1")
                    .imageThumbnail("user1 image Thumbnail")
                    .build();

            when(userService.getUserInfo(any())).thenReturn(userInfo);
            // when
            // then
            mockMvc.perform(get("/user/info"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.data.name").value("user1"))
                    .andExpect(jsonPath("$.data.email").value("user1@gmail.com"))
                    .andExpect(jsonPath("$.data.nickname").value("user1"))
                    .andExpect(jsonPath("$.data.imageThumbnail").value("user1 image Thumbnail"));
        }

        @Test
        @DisplayName("실패 - 사용자 정보 없음")
        void getUserInfo_UserNotFoundException() throws Exception {
            // given
            doThrow(UserNotFoundException.class).when(userService).getUserInfo(any());

            // when
            // then
            mockMvc.perform(get("/user/info"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.message").value(USER_NOT_FOUND.getMessage()))
                    .andExpect(jsonPath("$.result").value(false));
        }
    }
}
