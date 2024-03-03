package com.triportreat.backend.plan.service;

import static com.triportreat.backend.common.response.FailMessage.PLACE_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.plan.domain.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.domain.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.plan.error.exception.PlaceNotFoundException;
import com.triportreat.backend.plan.error.exception.UserNotFoundException;
import com.triportreat.backend.plan.repository.PlanRepository;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.repository.ScheduleRepository;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PlanServiceTest {

    @Autowired
    PlanService planService;

    @MockBean
    PlanRepository planRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ScheduleRepository scheduleRepository;

    @MockBean
    PlaceRepository placeRepository;

    @MockBean
    SchedulePlaceRepository schedulePlaceRepository;

    private PlanCreateRequestDto createPlanRequestDtoBeforeTest() {
        List<SchedulePlaceCreateRequestDto> schedulePlaceRequests1 = List.of(
                SchedulePlaceCreateRequestDto.builder().placeId(1L).build(),
                SchedulePlaceCreateRequestDto.builder().placeId(2L).build());

        List<SchedulePlaceCreateRequestDto> schedulePlaceRequests2 = List.of(
                SchedulePlaceCreateRequestDto.builder().placeId(3L).build(),
                SchedulePlaceCreateRequestDto.builder().placeId(4L).build());

        List<ScheduleCreateRequestDto> scheduleRequests = List.of(
                ScheduleCreateRequestDto.builder().date(LocalDate.now()).schedulePlaces(schedulePlaceRequests1).build(),
                ScheduleCreateRequestDto.builder().date(LocalDate.now().plusDays(1)).schedulePlaces(schedulePlaceRequests2).build());

        return PlanCreateRequestDto.builder()
                .userId(1L)
                .schedules(scheduleRequests)
                .build();
    }

    @Test
    @DisplayName("계획 저장 서비스 메소드 테스트")
    void createPlan() {
        // given
        PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();

        User user = User.builder().id(planCreateRequestDto.getUserId()).build();
        Plan plan = Plan.builder().id(1L).title(planCreateRequestDto.getTitle()).build();

        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(planRepository.save(any(Plan.class))).thenReturn(plan);
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(Place.builder().id(1L).build()));
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(Place.builder().id(2L).build()));
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(Place.builder().id(3L).build()));
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(Place.builder().id(4L).build()));

        planService.createPlan(planCreateRequestDto);

        // then
        verify(schedulePlaceRepository, times(4)).save(any(SchedulePlace.class));
        verify(scheduleRepository, times(2)).save(any(Schedule.class));
        verify(planRepository, times(1)).save(any(Plan.class));
    }

    @Test
    @DisplayName("계획저장할 때 사용자가 존재하지 않을시 예외발생")
    void createPlan_UserNotFoundException() {
        // given
        PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();

        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> planService.createPlan(planCreateRequestDto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("계획저장할 때 선택한 장소가 존재하지 않을시 예외발생")
    void createPlan_PlaceNotFoundException() {
        // given
        PlanCreateRequestDto planCreateRequestDto = createPlanRequestDtoBeforeTest();

        User user = User.builder().id(planCreateRequestDto.getUserId()).build();
        Plan plan = Plan.builder().id(1L).title(planCreateRequestDto.getTitle()).build();

        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(planRepository.save(any(Plan.class))).thenReturn(plan);
        when(placeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> planService.createPlan(planCreateRequestDto))
                .isInstanceOf(PlaceNotFoundException.class)
                .hasMessage(PLACE_NOT_FOUND.getMessage());
    }
}