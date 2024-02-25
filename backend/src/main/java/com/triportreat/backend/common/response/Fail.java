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
public class Fail {
    private String result;
    private String message;
    private int status;

    public static Fail response(String message) {
        return Fail.builder()
                .result("fail")
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
