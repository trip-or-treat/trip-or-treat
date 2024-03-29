package com.triportreat.backend.plan.error.exception;

import static com.triportreat.backend.common.response.FailMessage.SCHEDULE_PLACE_NOT_FOUND;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class SchedulePlaceNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return SCHEDULE_PLACE_NOT_FOUND.getMessage();
    }
}
