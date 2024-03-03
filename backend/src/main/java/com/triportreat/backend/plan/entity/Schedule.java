package com.triportreat.backend.plan.entity;

import com.triportreat.backend.common.BaseTimeEntity;
import com.triportreat.backend.plan.domain.ScheduleCreateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
@ToString(exclude = "schedulePlaces")
public class Schedule extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Builder.Default
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulePlace> schedulePlaces = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate visitDate;

    public static Schedule toEntity(ScheduleCreateRequestDto scheduleCreateRequestDto, Plan plan) {
        return Schedule.builder()
                .plan(plan)
                .visitDate(scheduleCreateRequestDto.getDate())
                .build();
    }
}
