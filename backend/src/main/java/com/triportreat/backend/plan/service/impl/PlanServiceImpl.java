package com.triportreat.backend.plan.service.impl;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.plan.domain.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.plan.repository.PlanRepository;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.repository.ScheduleRepository;
import com.triportreat.backend.plan.service.PlanService;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.util.List;
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
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다!"));  // TODO 사용자 존재하지 않음 예외 생성

        String code = "";  // TODO Plan Code
        Plan plan = Plan.toEntity(planCreateRequestDto, user, code);
        planRepository.save(plan);

        createSchedules(planCreateRequestDto.getSchedules(), plan);
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
            Place place = placeRepository.findById(sp.getPlaceId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 장소입니다!"));  // TODO 장소 존재하지 않음 예외 생성
            SchedulePlace schedulePlace = SchedulePlace.toEntity(sp, schedule, place);
            schedulePlaceRepository.save(schedulePlace);
        });
    }

}
