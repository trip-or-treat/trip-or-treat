package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.FailMessage.AUTHENTICATION_FAILED;
import static com.triportreat.backend.common.response.FailMessage.SCHEDULE_PLACE_NOT_FOUND;
import static com.triportreat.backend.common.response.SuccessMessage.DELETE_SUCCESS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.triportreat.backend.auth.filter.JwtAuthenticationFilter;
import com.triportreat.backend.auth.filter.JwtExceptionFilter;
import com.triportreat.backend.auth.utils.AuthUserArgumentResolver;
import com.triportreat.backend.common.config.WebConfig;
import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.plan.error.exception.SchedulePlaceNotFoundException;
import com.triportreat.backend.plan.service.SchedulePlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@WebMvcTest(controllers = SchedulePlaceController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtExceptionFilter.class,
                        JwtAuthenticationFilter.class,
                        AuthUserArgumentResolver.class,
                        WebConfig.class}))
class SchedulePlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulePlaceService schedulePlaceService;

    @Nested
    @DisplayName("스케줄-장소 삭제")
    class DeleteSchedulePlace {

        @Test
        @DisplayName("성공")
        void deleteSchedulePlace() throws Exception {
            // given
            Long userId = 1L;
            Long id = 1L;

            doNothing().when(schedulePlaceService).deleteSchedulePlace(id);

            // when
            // then
            mockMvc.perform(delete("/schedule-place/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                    .andExpect(jsonPath("$.message").value(DELETE_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 인증 실패")
        void deleteSchedulePlace_AuthenticateFail() throws Exception {
            // given
            Long userId = 1L;
            Long id = 1L;


            //validateSchedulePlaceOwner 메서드를 호출하는 케이스로 작성하면 테스트를 통과하지 못함
            doThrow(AuthenticateFailException.class).when(schedulePlaceService).deleteSchedulePlace(id);

            // when
            // then
            mockMvc.perform(delete("/schedule-place/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(AUTHENTICATION_FAILED.getMessage()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 스케줄-장소")
        void deleteSchedulePlace_NotFoundSchedulePlace() throws Exception {
            // given
            Long userId = 1L;
            Long id = 1L;

            //validateSchedulePlaceOwner 메서드를 호출하는 케이스로 작성하면 테스트를 통과하지 못함
            doThrow(SchedulePlaceNotFoundException.class).when(schedulePlaceService).deleteSchedulePlace(id);

            // when
            // then
            mockMvc.perform(delete("/schedule-place/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(SCHEDULE_PLACE_NOT_FOUND.getMessage()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
        }
    }

}