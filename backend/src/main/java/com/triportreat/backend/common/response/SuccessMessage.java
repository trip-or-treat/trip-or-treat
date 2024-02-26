package com.triportreat.backend.common.response;

public enum SuccessMessage {
        GET_SUCCESS("조회에 성공하였습니다.");

        private final String message;

        SuccessMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
}
