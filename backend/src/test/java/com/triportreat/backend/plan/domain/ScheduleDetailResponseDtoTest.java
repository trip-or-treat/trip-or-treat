package com.triportreat.backend.plan.domain;

import static org.assertj.core.api.Assertions.*;

import com.triportreat.backend.plan.entity.Schedule;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class ScheduleDetailResponseDtoTest {

    @Test
    void toDto() {
        // given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .visitDate(LocalDate.now())
                .build();
        List<SchedulePlaceDetailResponseDto> schedulePlaceDetails = List.of(
                SchedulePlaceDetailResponseDto.builder().schedulePlaceId(1L).build(),
                SchedulePlaceDetailResponseDto.builder().schedulePlaceId(2L).build()
        );

        // when
        ScheduleDetailResponseDto scheduleDetail = ScheduleDetailResponseDto.toDto(schedule, schedulePlaceDetails);

        // then
        assertThat(scheduleDetail.getScheduleId()).isEqualTo(1);
        assertThat(scheduleDetail.getDate()).isEqualTo(LocalDate.now());
        assertThat(scheduleDetail.getSchedulePlaces().size()).isEqualTo(2);
    }
}