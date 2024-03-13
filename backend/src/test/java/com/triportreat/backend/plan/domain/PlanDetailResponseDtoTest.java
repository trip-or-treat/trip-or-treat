package com.triportreat.backend.plan.domain;

import static org.assertj.core.api.Assertions.*;

import com.triportreat.backend.plan.entity.Plan;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlanDetailResponseDtoTest {

    @Test
    void toDto() {
        // given
        Plan plan = Plan.builder()
                .id(1L)
                .title("title")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .code("ABC")
                .build();
        List<ScheduleDetailResponseDto> schedules = List.of(
                ScheduleDetailResponseDto.builder().scheduleId(1L).build(),
                ScheduleDetailResponseDto.builder().scheduleId(2L).build());

        // when
        PlanDetailResponseDto planDetail = PlanDetailResponseDto.toDto(plan, schedules);

        // then
        assertThat(planDetail.getPlanId()).isEqualTo(1);
        assertThat(planDetail.getStartDate()).isEqualTo(LocalDate.now());
        assertThat(planDetail.getEndDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(planDetail.getCode()).isEqualTo("ABC");
        assertThat(planDetail.getSchedules().size()).isEqualTo(2);
    }
}