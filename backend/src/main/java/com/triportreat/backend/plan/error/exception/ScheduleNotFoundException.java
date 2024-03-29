package com.triportreat.backend.plan.error.exception;

import static com.triportreat.backend.common.response.FailMessage.SCHEDULE_NOT_FOUND;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return SCHEDULE_NOT_FOUND.getMessage();
    }
}
