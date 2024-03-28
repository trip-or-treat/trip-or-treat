package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.FailMessage.AUTHENTICATION_FAILED;
import static com.triportreat.backend.common.response.FailMessage.PLAN_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.REGION_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.VALIDATION_FAILED;
import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.PATCH_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;
import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_FALSE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.SEARCH_TYPE_REGION;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.triportreat.backend.auth.filter.JwtAuthenticationFilter;
import com.triportreat.backend.auth.filter.JwtExceptionFilter;
import com.triportreat.backend.auth.utils.AuthUserArgumentResolver;
import com.triportreat.backend.common.config.WebConfig;
import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanDetailResponseDto;
import com.triportreat.backend.common.response.PageResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.RegionResponseDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.ScheduleDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.SchedulePlaceDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import com.triportreat.backend.plan.error.exception.PlanNotFoundException;
import com.triportreat.backend.plan.error.exception.UserNotFoundException;
import com.triportreat.backend.plan.service.PlanService;
import com.triportreat.backend.region.error.exception.RegionNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(controllers = PlanController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtExceptionFilter.class,
                        JwtAuthenticationFilter.class,
                        AuthUserArgumentResolver.class,
                        WebConfig.class}))
class PlanControllerTest extends DummyObject {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PlanService planService;

    @Nested
    @DisplayName("계획 저장")
    class CreatePlan {

        @Test
        @DisplayName("성공")
        void createPlan() throws Exception {
            // given
            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();

            doNothing().when(planService).createPlan(planCreateRequestDto, 1L);

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

            doNothing().when(planService).createPlan(planCreateRequestDto, 1L);

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

            when(planService.getPlanDetail(anyLong(), any())).thenReturn(planDetail);

            // when
            // then
            mockMvc.perform(get("/plans/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.data.planId").value(1))
                    .andExpect(jsonPath("$.data.regions.regionIds.size()").value(3))
                    .andExpect(jsonPath("$.data.regions.regionNames.size()").value(3))
                    .andExpect(jsonPath("$.data.schedules[0].scheduleId").value(1))
                    .andExpect(jsonPath("$.data.schedules[1].scheduleId").value(2))
                    .andExpect(jsonPath("$.data.schedules[0].schedulePlaces[0].schedulePlaceId").value(1))
                    .andExpect(jsonPath("$.data.schedules[0].schedulePlaces[1].schedulePlaceId").value(2))
                    .andExpect(jsonPath("$.data.schedules[1].schedulePlaces[0].schedulePlaceId").value(3))
                    .andExpect(jsonPath("$.data.schedules[1].schedulePlaces[1].schedulePlaceId").value(4));
        }

        @Test
        @DisplayName("실패 - 사용자 정보 없음")
        void getPlanDetail_UserNotFound() throws Exception {
            // given
            when(planService.getPlanDetail(anyLong(), any())).thenThrow(UserNotFoundException.class);

            // when
            // then
            mockMvc.perform(get("/plans/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.message").value(USER_NOT_FOUND.getMessage()))
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.data").doesNotExist());
        }

        @Test
        @DisplayName("실패 - 계획 정보 없음")
        void getPlanDetail_PlanNotFound() throws Exception {
            // given
            when(planService.getPlanDetail(anyLong(), any())).thenThrow(PlanNotFoundException.class);

            // when
            // then
            mockMvc.perform(get("/plans/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.message").value(PLAN_NOT_FOUND.getMessage()))
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.data").doesNotExist());
        }

        @Test
        @DisplayName("실패 - 지역 정보 없음")
        void getPlanDetail_RegionNotFound() throws Exception {
            // given
            when(planService.getPlanDetail(anyLong(), any())).thenThrow(RegionNotFoundException.class);

            // when
            // then
            mockMvc.perform(get("/plans/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.message").value(REGION_NOT_FOUND.getMessage()))
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

            RegionResponseDto regions = RegionResponseDto.builder()
                    .regionIds(Arrays.asList(1L, 2L, 3L))
                    .regionNames(Arrays.asList("서울", "인천", "대전"))
                    .build();

            return PlanDetailResponseDto.builder()
                    .planId(1L)
                    .schedules(scheduleDetail)
                    .regions(regions)
                    .build();
        }
    }

    @Nested
    @DisplayName("계획 수정")
    class UpdatePlan extends DummyObject {

        @Test
        @DisplayName("성공")
        void updatePlan() throws Exception {
            // given
            Long id = 1L;
            Long userId = 1L;
            PlanUpdateRequestDto planUpdateRequestDto = createPlanUpdateRequestDto();
            planUpdateRequestDto.setPlanId(id);
            planUpdateRequestDto.setUserId(userId);

            doNothing().when(planService).updatePlan(planUpdateRequestDto);

            // when
            planService.updatePlan(planUpdateRequestDto);

            // then
            mockMvc.perform(patch("/plans/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(planUpdateRequestDto)))
                    .andExpect(result -> verify(planService).updatePlan(planUpdateRequestDto))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", equalTo(true)))
                    .andExpect(jsonPath("$.status", equalTo(OK.value())))
                    .andExpect(jsonPath("$.message", equalTo(PATCH_SUCCESS.getMessage())))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test
        @DisplayName("실패 - 계획과 사용자 불일치")
        void updatePlan_validatePlanOwner() throws Exception {
            // given
            Long id = 1L;
            Long userId = 2L;
            PlanUpdateRequestDto planUpdateRequestDto = createPlanUpdateRequestDto();
            planUpdateRequestDto.setPlanId(id);
            planUpdateRequestDto.setUserId(userId);

            // when
            doThrow(new AuthenticateFailException()).when(planService).updatePlan(any());

            // then
            mockMvc.perform(patch("/plans/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(planUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", equalTo(UNAUTHORIZED.value())))
                    .andExpect(jsonPath("$.message", equalTo(AUTHENTICATION_FAILED.getMessage())))
                    .andExpect(jsonPath("$.result", equalTo(false)))
                    .andExpect(jsonPath("$.data").doesNotExist());
        }

        @Test
        @DisplayName("실패 - 계획 정보 없음")
        void updatePlan_PlanNotFound() throws Exception {
            // given
            Long id = 2L;
            Long userId = 1L;
            PlanUpdateRequestDto planUpdateRequestDto = createPlanUpdateRequestDto();
            planUpdateRequestDto.setPlanId(id);
            planUpdateRequestDto.setUserId(userId);

            // when
            doThrow(PlanNotFoundException.class).when(planService).updatePlan(any());

            // then
            mockMvc.perform(patch("/plans/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(planUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", equalTo(INTERNAL_SERVER_ERROR.value())))
                    .andExpect(jsonPath("$.message", equalTo(PLAN_NOT_FOUND.getMessage())))
                    .andExpect(jsonPath("$.result", equalTo(false)))
                    .andExpect(jsonPath("$.data").doesNotExist());
        }
    }

    @Nested
    @DisplayName("내 계획 목록 검색 및 조회")
    class SearchPlans {

        @Test
        @DisplayName("성공")
        void searchPlans_Success() throws Exception {
            // given
            List<PlanListResponseDto> content = List.of(
                    PlanListResponseDto.builder()
                            .planId(1L)
                            .title("계획1")
                            .regions(List.of("서울", "인천", "대전"))
                            .startDate(LocalDate.now().minusDays(3))
                            .endDate(LocalDate.now().minusDays(1))
                            .createdDateTime(LocalDateTime.now().minusDays(7))
                            .build(),
                    PlanListResponseDto.builder()
                            .planId(2L)
                            .title("계획2")
                            .regions(List.of("서울", "인천", "대전"))
                            .startDate(LocalDate.now().minusDays(3))
                            .endDate(LocalDate.now().minusDays(1))
                            .createdDateTime(LocalDateTime.now().minusDays(7))
                            .build());

            Pageable pageable = PageRequest.of(0, 10);
            long total = 2L;
            PageImpl<PlanListResponseDto> response = new PageImpl<>(content, pageable, total);
            PageResponseDto<PlanListResponseDto> pageResponse = new PageResponseDto<>(response);

            when(planService.searchPlans(any(), any(), any())).thenReturn(pageResponse);

            // when
            // then
            mockMvc.perform(get("/plans")
                            .param("type", SEARCH_TYPE_REGION)
                            .param("keyword", "서울")
                            .param("comingYn", COMING_YN_FALSE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data.contents.size()").value(2));
        }

        @Test
        @DisplayName("실패 - 사용자가 존재하지 않음")
        void searchPlans_UserNotFound() throws Exception {
            // given
            when(planService.searchPlans(any(), any(), any())).thenThrow(UserNotFoundException.class);

            // when
            // then
            mockMvc.perform(get("/plans")
                            .param("type", SEARCH_TYPE_REGION)
                            .param("keyword", "서울")
                            .param("comingYn", COMING_YN_FALSE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(USER_NOT_FOUND.getMessage()))
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }
}