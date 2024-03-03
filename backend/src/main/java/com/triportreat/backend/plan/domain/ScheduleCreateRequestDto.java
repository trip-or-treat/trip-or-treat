package com.triportreat.backend.plan.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ScheduleCreateRequestDto {

    @NotNull(message = "스케쥴의 날짜는 필수 입력값입니다!")
    private LocalDate date;

    @Valid
    @Size(min = 1, message = "방문장소는 최소 1곳입니다!")
    private List<SchedulePlaceCreateRequestDto> schedulePlaces = new ArrayList<>();
}
