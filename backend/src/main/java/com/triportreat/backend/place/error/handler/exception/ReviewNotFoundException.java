package com.triportreat.backend.place.error.handler.exception;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

import static com.triportreat.backend.common.response.FailMessage.REVIEW_NOT_FOUND;

public class ReviewNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return REVIEW_NOT_FOUND.getMessage();
    }
}
