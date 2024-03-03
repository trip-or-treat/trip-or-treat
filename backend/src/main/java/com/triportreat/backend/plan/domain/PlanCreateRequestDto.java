package com.triportreat.backend.plan.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PlanCreateRequestDto {

    @NotEmpty(message = "제목은 필수 입력값입니다!")
    private String title;

    @NotNull(message = "여행 시작날짜는 필수 입력값입니다!")
    private LocalDate startDate;

    @NotNull(message = "여행 종료날짜는 필수 입력값입니다!")
    private LocalDate endDate;

    @NotNull(message = "사용자아이디는 필수 입력값입니다!")
    private Long userId;

    @Builder.Default
    @Valid
    @Size(min = 1, message = "스케쥴은 최소 하루는 있어야 합니다!")
    private List<ScheduleCreateRequestDto> schedules = new ArrayList<>();
}
