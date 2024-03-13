package com.triportreat.backend.dummy;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.user.entity.User;
import java.time.LocalDate;
import java.util.List;

public class DummyObject {

    protected User createMockUser(Long id, String name) {
        return User.builder()
                .id(id)
                .name(name)
                .nickname(name)
                .email(name + "@gmail.com")
                .imageThumbnail("")
                .build();
    }

    protected Plan createMockPlan(Long id, List<Schedule> schedules) {
        return Plan.builder()
                .id(id)
                .title("Plan")
                .schedules(schedules)
                .build();
    }

    protected Schedule createMockSchedule(Long id, LocalDate visitDate, List<SchedulePlace> schedulePlaces) {
        return Schedule.builder()
                .id(id)
                .schedulePlaces(schedulePlaces)
                .visitDate(visitDate)
                .build();
    }

    protected SchedulePlace createMockSchedulePlace(Long id, Long placeId, int visitOrder) {
        return SchedulePlace.builder()
                .id(id)
                .place(Place.builder().id(placeId).build())
                .visitOrder(visitOrder)
                .memo("memo")
                .expense(1000L)
                .build();
    }

    protected PlanCreateRequestDto createPlanRequestDto(LocalDate startDate, LocalDate endDate, Long userId, List<ScheduleCreateRequestDto> scheduleRequests) {
        return PlanCreateRequestDto.builder()
                .title("Plan")
                .startDate(startDate)
                .endDate(endDate)
                .userId(userId)
                .schedules(scheduleRequests)
                .build();
    }

    protected ScheduleCreateRequestDto createScheduleRequestDto(LocalDate date, List<SchedulePlaceCreateRequestDto> schedulePlaceRequests) {
        return ScheduleCreateRequestDto.builder()
                .date(date)
                .schedulePlaces(schedulePlaceRequests)
                .build();
    }

    protected SchedulePlaceCreateRequestDto createSchedulePlaceRequestDto(Long placeId, int visitOrder) {
        return SchedulePlaceCreateRequestDto.builder()
                .placeId(placeId)
                .memo("memo")
                .visitOrder(visitOrder)
                .expense(1000L)
                .build();
    }
}
