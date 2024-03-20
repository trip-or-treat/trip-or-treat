package com.triportreat.backend.common.response;

import lombok.Getter;

@Getter
public enum FailMessage {
    UNKNOWN_ERROR("알 수 없는 오류가 발생하였습니다."),
    REGION_NOT_FOUND("지역 정보가 존재하지 않습니다!"),
    RECOMMENDED_PLACE_EMPTY("추천 장소 정보가 존재하지 않습니다!"),
    API_CALL_FAILED("외부 API 호출에 실패하였습니다."),
    API_RESPONSE_PARSE_FAILED("외부 API 응답 처리에 실패하였습니다."),
    USER_NOT_FOUND("사용자가 존재하지 않습니다!"),
    PLACE_NOT_FOUND("장소가 존재하지 않습니다!"),
    VALIDATION_FAILED("유효성 검증에 실패하였습니다!"),
    PLAN_NOT_FOUND("계획이 존재하지 않습니다!"),
    SCHEDULE_PLACE_NOT_FOUND("스케줄-장소가 존재하지 않습니다!");

    private final String message;

    FailMessage(String message) {
        this.message = message;
    }
}
