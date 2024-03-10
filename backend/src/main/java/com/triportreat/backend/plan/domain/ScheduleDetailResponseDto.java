package com.triportreat.backend.plan.domain;

import com.triportreat.backend.plan.entity.Schedule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleDetailResponseDto {
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
