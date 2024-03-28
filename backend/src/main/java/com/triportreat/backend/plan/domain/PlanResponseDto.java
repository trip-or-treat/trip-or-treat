package com.triportreat.backend.plan.domain;

import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.PlanRegion;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class PlanResponseDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class PlanDetailResponseDto {
        private Long planId;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private String code;
        private RegionResponseDto regions;

        @Builder.Default
        private List<ScheduleDetailResponseDto> schedules = new ArrayList<>();

        public static PlanDetailResponseDto toDto(Plan plan, List<ScheduleDetailResponseDto> schedules) {
            return PlanDetailResponseDto.builder()
                    .planId(plan.getId())
                    .title(plan.getTitle())
                    .startDate(plan.getStartDate())
                    .endDate(plan.getEndDate())
                    .code(plan.getCode())
                    .regions(new RegionResponseDto(plan.getRegions()))
                    .schedules(schedules)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class RegionResponseDto {
        @Builder.Default
        private List<Long> regionIds = new ArrayList<>();

        @Builder.Default
        private List<String> regionNames = new ArrayList<>();

        public RegionResponseDto(Set<PlanRegion> planRegions) {
            this.regionIds = planRegions.stream()
                    .map(planRegion -> planRegion.getRegion().getId())
                    .collect(Collectors.toList());
            this.regionNames = planRegions.stream()
                    .map(planRegion -> planRegion.getRegion().getName())
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ScheduleDetailResponseDto {
        private Long scheduleId;
        private LocalDate date;

        @Builder.Default
        private List<SchedulePlaceDetailResponseDto> schedulePlaces = new ArrayList<>();

        public static ScheduleDetailResponseDto toDto(Schedule schedule, List<SchedulePlaceDetailResponseDto> schedulePlaces) {
            return ScheduleDetailResponseDto.builder()
                    .scheduleId(schedule.getId())
                    .date(schedule.getVisitDate())
                    .schedulePlaces(schedulePlaces)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class SchedulePlaceDetailResponseDto {
        private Long schedulePlaceId;
        private Long placeId;
        private String memo;
        private Integer visitOrder;
        private Long expense;
        private String imageThumbnail;
        private Long regionId;
        private String placeName;
        private Long contentTypeId;
        private String subCategoryName;
        private Double latitude;
        private Double longitude;

        public static SchedulePlaceDetailResponseDto toDto(SchedulePlace schedulePlace) {
            return SchedulePlaceDetailResponseDto.builder()
                    .schedulePlaceId(schedulePlace.getId())
                    .placeId(schedulePlace.getPlace().getId())
                    .memo(schedulePlace.getMemo())
                    .visitOrder(schedulePlace.getVisitOrder())
                    .expense(schedulePlace.getExpense())
                    .imageThumbnail(schedulePlace.getPlace().getImageThumbnail())
                    .regionId(schedulePlace.getPlace().getRegion().getId())
                    .placeName(schedulePlace.getPlace().getName())
                    .contentTypeId(schedulePlace.getPlace().getContentType().getId())
                    .subCategoryName(schedulePlace.getPlace().getSubCategory().getName())
                    .latitude(schedulePlace.getPlace().getLatitude())
                    .longitude(schedulePlace.getPlace().getLongitude())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class PlanListResponseDto {
        private Long planId;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        @JsonIgnore
        private LocalDateTime createdDateTime;
        private String createdDate;

        @Builder.Default
        private List<String> regions = new ArrayList<>();
    }
}
