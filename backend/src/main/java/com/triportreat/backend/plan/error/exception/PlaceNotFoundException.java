package com.triportreat.backend.plan.error.exception;

import static com.triportreat.backend.common.response.FailMessage.PLACE_NOT_FOUND;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class PlaceNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return PLACE_NOT_FOUND.getMessage();
    }
}
