package com.triportreat.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    REGION_NOT_FOUND("지역 정보가 존재하지 않습니다!"),
    RECOMMENDED_PLACE_EMPTY("추천 장소 정보가 존재하지 않습니다!");

    private final String message;
}
