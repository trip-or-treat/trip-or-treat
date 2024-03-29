package com.triportreat.backend.common.response;

import lombok.Getter;

@Getter
public enum SuccessMessage {
        GET_SUCCESS("조회에 성공하였습니다."),
        POST_SUCCESS("저장에 성공하였습니다."),
        PUT_SUCCESS("수정에 성공하였습니다."),
        LOGIN_SUCCESS("로그인에 성공하였습니다."),
        PATCH_SUCCESS("부분 수정에 성공하였습니다."),
        DELETE_SUCCESS("삭제에 성공하였습니다.");

        private final String message;

        SuccessMessage(String message) {
            this.message = message;
        }
}
