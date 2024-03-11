package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.FailMessage.PLAN_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.VALIDATION_FAILED;
import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.ScheduleDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.domain.SchedulePlaceDetailResponseDto;
import com.triportreat.backend.plan.error.exception.PlanNotFoundException;
import com.triportreat.backend.plan.service.PlanService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PlanController.class)
@AutoConfigureMockMvc
class PlanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PlanService planService;

    @Nested
    @DisplayName("계획 저장")
    class CreatePlan {

        @Test
        @DisplayName("성공")
        void createPlan() throws Exception {
            // given
            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();

            doNothing().when(planService).createPlan(planCreateRequestDto);

            // when
            // then
            mockMvc.perform(post("/plans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(planCreateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", equalTo(200)))
                    .andExpect(jsonPath("$.message", equalTo(POST_SUCCESS.getMessage())))
                    .andExpect(jsonPath("$.result", equalTo(true)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test()
        @DisplayName("실패 - 유효성 검증 실패")
        void createPlan_UserNotFoundException() throws Exception {
            // given
            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();
            planCreateRequestDto.setTitle("");

            doNothing().when(planService).createPlan(planCreateRequestDto);

            // when
            // then
            mockMvc.perform(post("/plans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(planCreateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(VALIDATION_FAILED.getMessage())))
                    .andExpect(jsonPath("$.result", equalTo(false)));
        }

        private PlanCreateRequestDto createPlanRequestDtoBeforeTest() {
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests1 = List.of(
                    SchedulePlaceCreateRequestDto.builder()
                            .placeId(1L)
                            .visitOrder(1)
                            .memo("memo")
                            .expense(1000L)
                            .build(),
                    SchedulePlaceCreateRequestDto.builder().placeId(1L)
                            .visitOrder(2)
                            .memo("memo")
                            .expense(2000L)
                            .build());

            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests2 = List.of(
                    SchedulePlaceCreateRequestDto.builder()
                            .placeId(3L)
                            .visitOrder(3)
                            .memo("memo")
                            .expense(3000L)
                            .build(),
                    SchedulePlaceCreateRequestDto.builder().placeId(1L)
                            .visitOrder(4)
                            .memo("memo")
                            .expense(4000L)
                            .build());

            List<ScheduleCreateRequestDto> scheduleRequests = List.of(
                    ScheduleCreateRequestDto.builder().date(LocalDate.now()).schedulePlaces(schedulePlaceRequests1).build(),
                    ScheduleCreateRequestDto.builder().date(LocalDate.now().plusDays(1)).schedulePlaces(schedulePlaceRequests2).build());

            return PlanCreateRequestDto.builder()
                    .userId(1L)
                    .title("title")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(1))
                    .schedules(scheduleRequests)
                    .build();
        }
    }

    @Nested
    @DisplayName("계획 상세 조회")
    class GetPlanDetail {

        @Test
        @DisplayName("성공")
        void getPlanDetail_Success() throws Exception {
            // given
            PlanDetailResponseDto planDetail = createPlanDetailResponseDto();

            when(planService.getPlanDetail(anyLong())).thenReturn(planDetail);

            // when
            // then
            mockMvc.perform(get("/plans/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.data.planId").value(1))
                    .andExpect(jsonPath("$.data.schedules[0].scheduleId").value(1))
                    .andExpect(jsonPath("$.data.schedules[1].scheduleId").value(2))
                    .andExpect(jsonPath("$.data.schedules[0].schedulePlaces[0].schedulePlaceId").value(1))
                    .andExpect(jsonPath("$.data.schedules[0].schedulePlaces[1].schedulePlaceId").value(2))
                    .andExpect(jsonPath("$.data.schedules[1].schedulePlaces[0].schedulePlaceId").value(3))
                    .andExpect(jsonPath("$.data.schedules[1].schedulePlaces[1].schedulePlaceId").value(4));

        }

        @Test
        @DisplayName("실패 - 계획 정보 없음")
        void getPlanDetail_PlanNotFound() throws Exception {
            // given
            when(planService.getPlanDetail(anyLong())).thenThrow(PlanNotFoundException.class);

            // when
            // then
            mockMvc.perform(get("/plans/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.message").value(PLAN_NOT_FOUND.getMessage()))
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.data").doesNotExist());
        }

        private PlanDetailResponseDto createPlanDetailResponseDto() {
            List<SchedulePlaceDetailResponseDto> schedulePlaceDetails1 = List.of(
                    SchedulePlaceDetailResponseDto.builder().schedulePlaceId(1L).build(),
                    SchedulePlaceDetailResponseDto.builder().schedulePlaceId(2L).build());
            List<SchedulePlaceDetailResponseDto> schedulePlaceDetails2 = List.of(
                    SchedulePlaceDetailResponseDto.builder().schedulePlaceId(3L).build(),
                    SchedulePlaceDetailResponseDto.builder().schedulePlaceId(4L).build());

            List<ScheduleDetailResponseDto> scheduleDetail = List.of(
                    ScheduleDetailResponseDto.builder().scheduleId(1L).schedulePlaces(schedulePlaceDetails1).build(),
                    ScheduleDetailResponseDto.builder().scheduleId(2L).schedulePlaces(schedulePlaceDetails2).build());

            return PlanDetailResponseDto.builder()
                    .planId(1L)
                    .schedules(scheduleDetail)
                    .build();
        }
    }
}