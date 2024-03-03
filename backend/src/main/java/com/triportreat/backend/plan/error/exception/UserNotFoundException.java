package com.triportreat.backend.plan.error.exception;

import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return USER_NOT_FOUND.getMessage();
    }
}
