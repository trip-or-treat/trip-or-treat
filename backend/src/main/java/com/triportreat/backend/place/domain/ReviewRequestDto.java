package com.triportreat.backend.place.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequestDto {

    private Long userId;
    @NotNull(message = "placeId는 필수 입력값입니다.")
    private Long placeId;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private String tip;
    @NotNull(message = "별점은 필수 입력값입니다.")
    private Integer score;
}
