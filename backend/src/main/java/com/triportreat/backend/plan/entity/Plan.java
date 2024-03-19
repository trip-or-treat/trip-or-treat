package com.triportreat.backend.plan.entity;

import com.triportreat.backend.common.BaseTimeEntity;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.user.entity.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@ToString(exclude = "schedules")
public class Plan extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "plan")
    private Set<PlanRegion> regions = new HashSet<>();

    @Column(length = 20, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String code;

    public static Plan toEntity(PlanCreateRequestDto planCreateRequestDto, User user, String code) {
        return Plan.builder()
                .user(user)
                .title(planCreateRequestDto.getTitle())
                .startDate(planCreateRequestDto.getStartDate())
                .endDate(planCreateRequestDto.getEndDate())
                .code(code)
                .build();
    }
}
