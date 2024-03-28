package com.triportreat.backend.common.error.exception;

import static com.triportreat.backend.common.response.FailMessage.AUTHENTICATION_FAILED;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class AuthenticateFailException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return AUTHENTICATION_FAILED.getMessage();
    }
}
