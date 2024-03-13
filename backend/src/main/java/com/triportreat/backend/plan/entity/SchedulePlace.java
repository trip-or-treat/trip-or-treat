package com.triportreat.backend.plan.entity;

import com.triportreat.backend.common.BaseTimeEntity;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class SchedulePlace extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(nullable = false)
    private Integer visitOrder;

    @Column(nullable = false)
    @Builder.Default
    private Long expense = 0L;

    @Column(length = 65535)
    private String memo;

    public static SchedulePlace toEntity(SchedulePlaceCreateRequestDto schedulePlaceCreateRequestDto,
                                         Schedule schedule,
                                         Place place) {
        return SchedulePlace.builder()
                .schedule(schedule)
                .place(place)
                .visitOrder(schedulePlaceCreateRequestDto.getVisitOrder())
                .expense(schedulePlaceCreateRequestDto.getExpense())
                .memo(schedulePlaceCreateRequestDto.getMemo())
                .build();
    }
}
