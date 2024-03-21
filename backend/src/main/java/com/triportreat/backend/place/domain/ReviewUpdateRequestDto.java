package com.triportreat.backend.place.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class ReviewUpdateRequestDto {

    @NotNull(message = "placeId는 필수입니다.")
    private final Long placeId;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private final String content;
    private final String tip;
    @NotNull(message = "별점은 필수 입력값입니다.")
    private final Integer score;
}