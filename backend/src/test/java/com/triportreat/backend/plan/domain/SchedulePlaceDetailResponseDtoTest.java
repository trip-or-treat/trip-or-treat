package com.triportreat.backend.plan.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.plan.entity.SchedulePlace;
import org.junit.jupiter.api.Test;

class SchedulePlaceDetailResponseDtoTest {

    @Test
    void toDto() {
        // given
        SchedulePlace schedulePlace = SchedulePlace.builder()
                .id(1L)
                .place(Place.builder().id(1L).build())
                .memo("memo")
                .visitOrder(1)
                .expense(10000L)
                .build();

        // when
        SchedulePlaceDetailResponseDto schedulePlaceDetail = SchedulePlaceDetailResponseDto.toDto(schedulePlace);

        // then
        assertThat(schedulePlaceDetail.getSchedulePlaceId()).isEqualTo(1);
        assertThat(schedulePlaceDetail.getPlaceId()).isEqualTo(1);
        assertThat(schedulePlaceDetail.getMemo()).isEqualTo("memo");
        assertThat(schedulePlaceDetail.getVisitOrder()).isEqualTo(1);
        assertThat(schedulePlaceDetail.getExpense()).isEqualTo(10000);
    }
}