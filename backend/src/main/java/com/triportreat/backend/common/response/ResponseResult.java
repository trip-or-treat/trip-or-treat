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
    private ResponseHeader header;
    private Object body;

    public static ResponseResult success(String message, Object data) {
        return ResponseResult.builder()
                .header(ResponseHeader.builder()
                        .result(true)
                        .message(message)
                        .status(HttpStatus.OK.value())
                        .build())
                .body(data)
                .build();
    }

    public static ResponseResult fail(String message, Object data) {
        return ResponseResult.builder()
                .header(ResponseHeader.builder()
                        .result(false)
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build())
                .body(data)
                .build();
    }
}
