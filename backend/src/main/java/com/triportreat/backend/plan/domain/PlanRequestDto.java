package com.triportreat.backend.plan.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class PlanRequestDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class PlanCreateRequestDto {

        @NotEmpty(message = "제목은 필수 입력값입니다!")
        private String title;

        @NotNull(message = "여행 시작날짜는 필수 입력값입니다!")
        private LocalDate startDate;

        @NotNull(message = "여행 종료날짜는 필수 입력값입니다!")
        private LocalDate endDate;

        private Long userId;

        @Builder.Default
        @Valid
        @Size(min = 1, message = "스케쥴은 최소 하루는 있어야 합니다!")
        private List<ScheduleCreateRequestDto> schedules = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ScheduleCreateRequestDto {

        @NotNull(message = "스케쥴의 날짜는 필수 입력값입니다!")
        private LocalDate date;

        @Builder.Default
        @Valid
        @Size(min = 1, message = "방문장소는 최소 1곳입니다!")
        private List<SchedulePlaceCreateRequestDto> schedulePlaces = new ArrayList<>();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class SchedulePlaceCreateRequestDto {

        @NotNull(message = "장소아이디는 필수 입력값입니다!")
        private Long placeId;

        @Size(max = 65535, message = "메모의 최대길이는 65535자입니다!")
        private String memo;

        @NotNull(message = "방문순서는 필수 입력값입니다!")
        @Min(value = 1, message = "순서는 최소 1부터입니다!")
        private Integer visitOrder;

        @Min(value = 0, message = "경비는 최소 0원부터입니다!")
        private Long expense;
    }

    @Getter
    @Setter
    public static class PlanUpdateRequestDto {

        private Long userId;

        private Long planId;

        @NotEmpty(message = "제목은 필수 입력값입니다!")
        private String title;

        @Builder.Default
        @Valid
        @Size(min = 1, message = "스케쥴은 최소 하루는 있어야 합니다!")
        private List<ScheduleUpdateRequestDto> schedules = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class ScheduleUpdateRequestDto {

        @NotNull(message = "스케쥴아이디는 필수 입력값입니다!")
        private Long scheduleId;

        @Builder.Default
        @Valid
        @Size(min = 1, message = "방문장소는 최소 1곳입니다!")
        private List<SchedulePlaceUpdateRequestDto> schedulePlaces = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class SchedulePlaceUpdateRequestDto {

        private Optional<Long> schedulePlaceId;

        @NotNull(message = "장소아이디는 필수 입력값입니다!")
        private Long placeId;

        @Size(max = 65535, message = "메모의 최대길이는 65535자입니다!")
        private String memo;

        @NotNull(message = "방문순서는 필수 입력값입니다!")
        @Min(value = 1, message = "순서는 최소 1부터입니다!")
        private Integer visitOrder;

        @Min(value = 0, message = "경비는 최소 0원부터입니다!")
        private Long expense;
    }
}
