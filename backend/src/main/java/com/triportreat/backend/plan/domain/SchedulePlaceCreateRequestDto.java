package com.triportreat.backend.plan.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class SchedulePlaceCreateRequestDto {

    @NotNull(message = "장소아이디는 필수 입력값입니다!")
    private Long placeId;

    @Size(max = 65535, message = "메모의 최대길이는 65535자입니다!")
    private String memo;

    @NotNull(message = "방문순서는 필수 입력값입니다!")
    @Min(value = 1, message = "순서는 최소 1부터입니다!")
    private Integer visitOrder;

    @Min(value = 0, message = "경비는 최소 0원부터입니다!")
    private Long expense;
}
