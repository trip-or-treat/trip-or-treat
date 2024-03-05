package com.triportreat.backend.common.response;

import lombok.Getter;

@Getter
public enum FailMessage {
    GET_FAIL("조회에 실패하였습니다."),
    UNKNOWN_ERROR("알 수 없는 오류가 발생하였습니다."),
    REGION_NOT_FOUND("지역 정보가 존재하지 않습니다!"),
    RECOMMENDED_PLACE_EMPTY("추천 장소 정보가 존재하지 않습니다!"),
    PLACE_NOT_FOUND("장소 정보가 존재하지 않습니다: "),
    API_CALL_FAILED("외부 API 호출에 실패하였습니다."),
    API_RESPONSE_PARSE_FAILED("외부 API 응답 처리에 실패하였습니다.");

    private final String message;

    FailMessage(String message) {
        this.message = message;
    }
}
