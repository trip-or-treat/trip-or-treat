package com.triportreat.backend.common.error.handler;

import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.common.response.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticateFailException.class)
    protected ResponseEntity<?> AuthenticateFailExceptionHandler(AuthenticateFailException e) {
        return ResponseEntity.ok().body(ResponseResult.fail(e.getMessage(), e.getStatus(), null));
    }
}
