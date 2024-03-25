package com.triportreat.backend.plan.service;

import static com.triportreat.backend.common.response.FailMessage.PLACE_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.PLAN_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.plan.domain.PlanDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.plan.error.exception.PlaceNotFoundException;
import com.triportreat.backend.plan.error.exception.PlanNotFoundException;
import com.triportreat.backend.plan.error.exception.UserNotFoundException;
import com.triportreat.backend.plan.repository.PlanRepository;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.repository.ScheduleRepository;
import com.triportreat.backend.plan.service.impl.PlanServiceImpl;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest extends DummyObject {

    @InjectMocks
    PlanServiceImpl planService;

    @Mock
    PlanRepository planRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    PlaceRepository placeRepository;

    @Mock
    SchedulePlaceRepository schedulePlaceRepository;

    @Nested
    @DisplayName("계획 저장")
    class CreatePlan {

        @Test
        @DisplayName("성공")
        void createPlan() {
            // given
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests1 = List.of(
                    createSchedulePlaceRequestDto(1L, 1),
                    createSchedulePlaceRequestDto(2L, 2));
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests2 = List.of(
                    createSchedulePlaceRequestDto(3L, 1),
                    createSchedulePlaceRequestDto(4L, 2));

            List<ScheduleCreateRequestDto> scheduleRequests = List.of(
                    createScheduleRequestDto(LocalDate.now(), schedulePlaceRequests1),
                    createScheduleRequestDto(LocalDate.now().plusDays(1), schedulePlaceRequests2));

            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDto(LocalDate.now(), LocalDate.now().plusDays(1), 1L, scheduleRequests);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(createMockUser(1L, "user1")));
            when(planRepository.save(any(Plan.class))).thenReturn(createMockPlan(1L, null));
            when(placeRepository.findById(anyLong())).thenReturn(Optional.of(Place.builder().id(1L).build()));

            // when
            planService.createPlan(planCreateRequestDto);

            // then
            verify(schedulePlaceRepository, times(4)).save(any(SchedulePlace.class));
            verify(scheduleRepository, times(2)).save(any(Schedule.class));
            verify(planRepository, times(1)).save(any(Plan.class));
        }

        @Test
        @DisplayName("실패 - 사용자가 존재하지 않을시 예외발생")
        void createPlan_UserNotFoundException() {
            // given
            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDto(LocalDate.now(), LocalDate.now().plusDays(1), 1L, null);

            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.createPlan(planCreateRequestDto))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("실패 - 선택한 장소가 존재하지 않을시 예외발생")
        void createPlan_PlaceNotFoundException() {
            // given
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests1 = List.of(
                    createSchedulePlaceRequestDto(1L, 1),
                    createSchedulePlaceRequestDto(2L, 2));
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests2 = List.of(
                    createSchedulePlaceRequestDto(3L, 1),
                    createSchedulePlaceRequestDto(4L, 2));

            List<ScheduleCreateRequestDto> scheduleRequests = List.of(
                    createScheduleRequestDto(LocalDate.now(), schedulePlaceRequests1),
                    createScheduleRequestDto(LocalDate.now().plusDays(1), schedulePlaceRequests2));

            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDto(LocalDate.now(), LocalDate.now().plusDays(1), 1L, scheduleRequests);
            User user = createMockUser(planCreateRequestDto.getUserId(), "user1");
            Plan plan = createMockPlan(1L, null);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(planRepository.save(any(Plan.class))).thenReturn(plan);
            when(placeRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.createPlan(planCreateRequestDto))
                    .isInstanceOf(PlaceNotFoundException.class)
                    .hasMessage(PLACE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("계획 상세 조회")
    class GetPlanDetail {

        @Test
        @DisplayName("성공")
        void getPlanDetail_Success() {
            // given
            List<SchedulePlace> schedulePlaces1 = List.of(
                    createMockSchedulePlace(1L, 1L, 1),
                    createMockSchedulePlace(2L, 2L, 2));
            List<SchedulePlace> schedulePlaces2 = List.of(
                    createMockSchedulePlace(3L, 3L, 3),
                    createMockSchedulePlace(4L, 4L, 4));
            List<Schedule> schedules = List.of(
                    createMockSchedule(1L, LocalDate.now(), schedulePlaces1),
                    createMockSchedule(2L, LocalDate.now().plusDays(1), schedulePlaces2));
            Plan plan = createMockPlan(1L, schedules);

            when(planRepository.findById(anyLong())).thenReturn(Optional.of(plan));

            // when
            PlanDetailResponseDto planDetail = planService.getPlanDetail(1L);

            // then
            assertThat(planDetail.getSchedules().size()).isEqualTo(2);
            assertThat(planDetail.getPlanId()).isEqualTo(1L);
            assertThat(planDetail.getSchedules().size()).isEqualTo(2);
            assertThat(planDetail.getSchedules().get(0).getSchedulePlaces().size()).isEqualTo(2);
            assertThat(planDetail.getSchedules().get(1).getSchedulePlaces().size()).isEqualTo(2);
        }

        @Test
        @DisplayName("실패 - 계획 데이터 없음")
        void getPlanDetail_PlanNotFound() {
            // given
            Plan plan = createMockPlan(1L, null);

            when(planRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.getPlanDetail(plan.getId()))
                    .isInstanceOf(PlanNotFoundException.class)
                    .hasMessage(PLAN_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("공유 계획 상세 조회")
    class GetSharedPlanDetail {

        @Test
        @DisplayName("성공")
        void getSharedPlanDetail_Success() {
            // given
            List<SchedulePlace> schedulePlaces1 = List.of(
                    createMockSchedulePlace(1L, 1L, 1),
                    createMockSchedulePlace(2L, 2L, 2));
            List<SchedulePlace> schedulePlaces2 = List.of(
                    createMockSchedulePlace(3L, 3L, 3),
                    createMockSchedulePlace(4L, 4L, 4));
            List<Schedule> schedules = List.of(
                    createMockSchedule(1L, LocalDate.now(), schedulePlaces1),
                    createMockSchedule(2L, LocalDate.now().plusDays(1), schedulePlaces2));
            Plan plan = createMockPlan(1L, schedules);

            when(planRepository.findByCode(anyString())).thenReturn(Optional.of(plan));

            // when
            PlanDetailResponseDto planDetail = planService.getSharedPlanDetail("code");

            // then
            assertThat(planDetail.getSchedules().size()).isEqualTo(2);
            assertThat(planDetail.getPlanId()).isEqualTo(1L);
            assertThat(planDetail.getSchedules().size()).isEqualTo(2);
            assertThat(planDetail.getSchedules().get(0).getSchedulePlaces().size()).isEqualTo(2);
            assertThat(planDetail.getSchedules().get(1).getSchedulePlaces().size()).isEqualTo(2);
        }

        @Test
        @DisplayName("실패 - 계획 데이터 없음")
        void getSharedPlanDetail_PlanNotFound() {
            // given
            Plan plan = createMockPlan(1L, null);

            when(planRepository.findByCode(anyString())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.getSharedPlanDetail(plan.getCode()))
                    .isInstanceOf(PlanNotFoundException.class)
                    .hasMessage(PLAN_NOT_FOUND.getMessage());
        }
    }
}