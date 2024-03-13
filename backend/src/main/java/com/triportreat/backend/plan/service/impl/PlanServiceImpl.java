package com.triportreat.backend.plan.service.impl;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.plan.domain.PlanDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.domain.ScheduleDetailResponseDto;
import com.triportreat.backend.plan.domain.SchedulePlaceDetailResponseDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.plan.error.exception.PlaceNotFoundException;
import com.triportreat.backend.plan.error.exception.PlanNotFoundException;
import com.triportreat.backend.plan.error.exception.UserNotFoundException;
import com.triportreat.backend.plan.repository.PlanRepository;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.repository.ScheduleRepository;
import com.triportreat.backend.plan.service.PlanService;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final PlaceRepository placeRepository;
    private final SchedulePlaceRepository schedulePlaceRepository;

    @Transactional
    @Override
    public void createPlan(PlanCreateRequestDto planCreateRequestDto) {
        User user = userRepository.findById(planCreateRequestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Plan plan = Plan.toEntity(planCreateRequestDto, user, createPlanCode());
        planRepository.save(plan);

        createSchedules(planCreateRequestDto.getSchedules(), plan);
    }

    @Transactional(readOnly = true)
    @Override
    public PlanDetailResponseDto getPlanDetail(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow(PlanNotFoundException::new);
        return PlanDetailResponseDto.toDto(plan, extractScheduleDetailsFromPlan(plan));
    }

    private void createSchedules(List<ScheduleCreateRequestDto> schedulesRequests, Plan plan) {
        schedulesRequests.forEach(s -> {
            Schedule schedule = Schedule.toEntity(s, plan);
            scheduleRepository.save(schedule);
            createSchedulePlaces(s.getSchedulePlaces(), schedule);
        });
    }

    private void createSchedulePlaces(List<SchedulePlaceCreateRequestDto> schedulePlaceRequests, Schedule schedule) {
        schedulePlaceRequests.forEach(sp -> {
            Place place = placeRepository.findById(sp.getPlaceId()).orElseThrow(PlaceNotFoundException::new);
            SchedulePlace schedulePlace = SchedulePlace.toEntity(sp, schedule, place);
            schedulePlaceRepository.save(schedulePlace);
        });
    }

    private String createPlanCode() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    private List<ScheduleDetailResponseDto> extractScheduleDetailsFromPlan(Plan plan) {
        return plan.getSchedules().stream()
                .map(schedule -> ScheduleDetailResponseDto.toDto(schedule, extractSchedulePlaceDetailsFromSchedule(schedule)))
                .collect(Collectors.toList());
    }

    private List<SchedulePlaceDetailResponseDto> extractSchedulePlaceDetailsFromSchedule(Schedule schedule) {
        return schedule.getSchedulePlaces().stream()
                .map(SchedulePlaceDetailResponseDto::toDto)
                .collect(Collectors.toList());
    }
}
