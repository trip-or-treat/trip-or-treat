package com.triportreat.backend.place.error.handler.exception;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;

public class UserNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return USER_NOT_FOUND.getMessage();
    }
}
