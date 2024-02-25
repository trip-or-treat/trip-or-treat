package com.triportreat.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {
    private Boolean result;
    private  String message;
    private int status;
    private Object data;

    public static ResponseResult success(String message, Object data) {
        return ResponseResult.builder()
                .result(true)
                .message(message)
                .status(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    public static ResponseResult fail(String message, HttpStatus status, Object data) {
        return ResponseResult.builder()
                .result(false)
                .message(message)
                .status(status.value())
                .data(data)
                .build();
    }
}
