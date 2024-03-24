package com.triportreat.backend.dummy;

import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.domain.ReviewUpdateRequestDto;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleUpdateRequestDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.user.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    protected Review createMockReview(Long id, User user, Place place) {
        return Review.builder()
                .id(id)
                .user(user)
                .place(place)
                .tip("testTip")
                .content("testContent")
                .score(5)
                .build();
    }

    protected ReviewRequestDto createReviewRequestDto(Long userId, Long placeId) {
        return ReviewRequestDto.builder()
                .userId(userId)
                .placeId(placeId)
                .content("testContent")
                .tip("testTip")
                .score(5)
                .build();
    }

    protected ReviewUpdateRequestDto createReviewUpdateRequestDto(Long id) {
        return ReviewUpdateRequestDto.builder()
                .placeId(id)
                .content("newContent")
                .tip("newTip")
                .score(1)
                .build();
    }

    protected PlanUpdateRequestDto createPlanUpdateRequestDto() {
        List<SchedulePlaceUpdateRequestDto> mockSchedulePlaces = List.of(
                SchedulePlaceUpdateRequestDto.builder()
                        .schedulePlaceId(Optional.of(1L))
                        .placeId(1L)
                        .visitOrder(1)
                        .memo("수정된 메모")
                        .expense(1000L)
                        .build(),
                SchedulePlaceUpdateRequestDto.builder()
                        .schedulePlaceId(Optional.empty())
                        .placeId(2L)
                        .visitOrder(2)
                        .memo("새로 추가된 스케줄-장소의 메모")
                        .expense(2000L)
                        .build());

        List<ScheduleUpdateRequestDto> mockSchedules = List.of(
                ScheduleUpdateRequestDto.builder()
                        .scheduleId(1L)
                        .schedulePlaces(mockSchedulePlaces)
                        .build());

        return PlanUpdateRequestDto.builder()
                .userId(1L)
                .planId(1L)
                .title("수정한 계획 제목")
                .schedules(mockSchedules)
                .build();
    }
}
