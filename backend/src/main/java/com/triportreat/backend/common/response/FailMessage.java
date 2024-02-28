package com.triportreat.backend.common.response;

import lombok.Getter;

@Getter
public enum FailMessage {
    GET_FAIL("조회에 실패하였습니다."),
    UNKNOWN_ERROR("알 수 없는 오류가 발생하였습니다.");

    private final String message;

    FailMessage(String message) {
        this.message = message;
    }
}
