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
public class Success {
    private String result;
    private String message;
    private int status;
    private Object data;

    public static Success response(String message, Object data) {
        return Success.builder()
                .result("success")
                .message(message)
                .status(HttpStatus.OK.value())
                .data(data)
                .build();
    }
}
