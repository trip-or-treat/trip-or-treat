package com.triportreat.backend.plan.domain;

import com.triportreat.backend.plan.entity.SchedulePlace;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchedulePlaceDetailResponseDto {
    private Long schedulePlaceId;
    private Long placeId;
    private String memo;
    private Integer visitOrder;
    private Long expense;

    public static SchedulePlaceDetailResponseDto toDto(SchedulePlace schedulePlace) {
        return SchedulePlaceDetailResponseDto.builder()
                .schedulePlaceId(schedulePlace.getId())
                .placeId(schedulePlace.getPlace().getId())
                .memo(schedulePlace.getMemo())
                .visitOrder(schedulePlace.getVisitOrder())
                .expense(schedulePlace.getExpense())
                .build();
    }
}
