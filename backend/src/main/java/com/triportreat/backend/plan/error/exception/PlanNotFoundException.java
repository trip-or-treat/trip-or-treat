package com.triportreat.backend.plan.error.exception;

import static com.triportreat.backend.common.response.FailMessage.PLAN_NOT_FOUND;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class PlanNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return PLAN_NOT_FOUND.getMessage();
    }
}
