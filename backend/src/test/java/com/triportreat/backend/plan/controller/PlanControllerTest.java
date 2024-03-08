package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.FailMessage.VALIDATION_FAILED;
import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.triportreat.backend.plan.domain.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.service.PlanService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
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

    @MockBean
    PlanService planService;

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

    @Test
    @DisplayName("계획저장 컨트롤러 메소드 테스트")
    void createPlan() throws Exception {
        // given
        PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();

        // when
        doNothing().when(planService).createPlan(planCreateRequestDto);

        // then
        mockMvc.perform(post("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(planCreateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(200)))
                .andExpect(jsonPath("$.message", equalTo(POST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.result", equalTo(true)))
                .andExpect(jsonPath("$.data", equalTo(null)));
    }

    @Test()
    @DisplayName("계획저장 컨트롤러 메소드 유효성 검증 실패 예외발생테스트")
    void createPlan_UserNotFoundException() throws Exception {
        // given
        PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();
        planCreateRequestDto.setTitle("");

        // when
        doNothing().when(planService).createPlan(planCreateRequestDto);

        // then
        mockMvc.perform(post("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(planCreateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo(VALIDATION_FAILED.getMessage())))
                .andExpect(jsonPath("$.result", equalTo(false)));
    }
}