package com.triportreat.backend.plan.domain;

import com.triportreat.backend.plan.entity.Plan;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlanDetailResponseDto {
    private Long planId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String code;

    @Builder.Default
    private List<ScheduleDetailResponseDto> schedules = new ArrayList<>();

    public static PlanDetailResponseDto toDto(Plan plan, List<ScheduleDetailResponseDto> schedules) {
        return PlanDetailResponseDto.builder()
                .planId(plan.getId())
                .title(plan.getTitle())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .code(plan.getCode())
                .schedules(schedules)
                .build();
    }
}
