package com.triportreat.backend.plan.service.impl;

import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.common.response.PageResponseDto;
import com.triportreat.backend.common.utils.CustomDateUtil;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.ScheduleDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.SchedulePlaceDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.PlanRegion;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.plan.error.exception.PlaceNotFoundException;
import com.triportreat.backend.plan.error.exception.PlanNotFoundException;
import com.triportreat.backend.plan.error.exception.ScheduleNotFoundException;
import com.triportreat.backend.plan.error.exception.SchedulePlaceNotFoundException;
import com.triportreat.backend.plan.error.exception.UserNotFoundException;
import com.triportreat.backend.plan.repository.PlanRegionRepository;
import com.triportreat.backend.plan.repository.PlanRepository;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.repository.ScheduleRepository;
import com.triportreat.backend.plan.service.PlanService;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.error.exception.RegionNotFoundException;
import com.triportreat.backend.region.repository.RegionRepository;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final PlaceRepository placeRepository;
    private final SchedulePlaceRepository schedulePlaceRepository;
    private final RegionRepository regionRepository;
    private final PlanRegionRepository planRegionRepository;

    @Override
    public void validatePlanOwner(Long id, Long userId) {
        if (!planRepository.existsByIdAndUserId(id, userId)) {
            throw new AuthenticateFailException();
        }
    }

    @Transactional
    @Override
    public void createPlan(PlanCreateRequestDto planCreateRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Plan plan = Plan.toEntity(planCreateRequestDto, user, createPlanCode());
        planRepository.save(plan);

        createPlanRegions(planCreateRequestDto.getRegions(), plan);
        createSchedules(planCreateRequestDto.getSchedules(), plan);
    }

    @Override
    public PlanDetailResponseDto getPlanDetail(Long planId, Long userId) {
        Plan plan = planRepository.findByIdAndUserId(planId, userId).orElseThrow(AuthenticateFailException::new);
        return PlanDetailResponseDto.toDto(plan, extractScheduleDetailsFromPlan(plan));
    }

    @Transactional
    @Override
    public void updatePlan(PlanUpdateRequestDto planUpdateRequestDto) {
        Plan plan = planRepository.findById(planUpdateRequestDto.getPlanId())
                .orElseThrow(PlanNotFoundException::new);

        validatePlanOwner(planUpdateRequestDto.getPlanId(), planUpdateRequestDto.getUserId());

        plan.updateTitle(planUpdateRequestDto.getTitle());

        updateSchedules(planUpdateRequestDto.getSchedules());
    }

    @Override
    public PageResponseDto<PlanListResponseDto> searchPlans(PlanSearchRequestDto condition, Pageable pageable, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        Page<PlanListResponseDto> response = planRepository.searchPlans(condition, pageable, userId);
        response.forEach(planListResponseDto ->
                planListResponseDto.setCreatedDate(CustomDateUtil.toStringFormat(planListResponseDto.getCreatedDateTime())));

        return new PageResponseDto<>(response);
    }

    private void updateSchedules(List<ScheduleUpdateRequestDto> scheduleDtos) {
        scheduleDtos.forEach(scheduleDto -> {
            Schedule schedule = scheduleRepository.findByIdWithSchedulePlacesFetchJoin(scheduleDto.getScheduleId())
                    .orElseThrow(ScheduleNotFoundException::new);

            schedule.getSchedulePlaces().clear();

            updateSchedulePlaces(schedule, scheduleDto.getSchedulePlaces());
        });
    }

    private void updateSchedulePlaces(Schedule schedule, List<SchedulePlaceUpdateRequestDto> schedulePlaceDtos) {
        schedulePlaceDtos.forEach(schedulePlaceDto -> {
            schedulePlaceDto.getSchedulePlaceId().ifPresentOrElse(
                    schedulePlaceId -> {
                        SchedulePlace schedulePlace = schedulePlaceRepository.findById(schedulePlaceId)
                                .orElseThrow(SchedulePlaceNotFoundException::new);
                        schedulePlace.update(schedulePlaceDto);

                        schedule.getSchedulePlaces().add(schedulePlace);
                    },
                    () -> {
                        Place place = placeRepository.findById(schedulePlaceDto.getPlaceId())
                                .orElseThrow(PlaceNotFoundException::new);

                        schedulePlaceRepository.save(SchedulePlace.toEntity(schedulePlaceDto, schedule, place));
                    }
            );
        });
    }

    private void createSchedules(List<ScheduleCreateRequestDto> schedulesRequests, Plan plan) {
        schedulesRequests.forEach(scheduleRequest -> {
            Schedule schedule = Schedule.toEntity(scheduleRequest, plan);
            scheduleRepository.save(schedule);
            createSchedulePlaces(scheduleRequest.getSchedulePlaces(), schedule);
        });
    }

    private void createSchedulePlaces(List<SchedulePlaceCreateRequestDto> schedulePlaceRequests, Schedule schedule) {
        schedulePlaceRequests.forEach(schedulePlaceRequest -> {
            Place place = placeRepository.findById(schedulePlaceRequest.getPlaceId()).orElseThrow(PlaceNotFoundException::new);
            SchedulePlace schedulePlace = SchedulePlace.toEntity(schedulePlaceRequest, schedule, place);
            schedulePlaceRepository.save(schedulePlace);
        });
    }

    private void createPlanRegions(List<Long> regionIds, Plan plan) {
        regionIds.forEach(regionId -> {
            Region region = regionRepository.findById(regionId).orElseThrow(RegionNotFoundException::new);
            planRegionRepository.save(PlanRegion.builder()
                    .plan(plan)
                    .region(region)
                    .build());
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
