package com.triportreat.backend.region.error.exception;

import static com.triportreat.backend.common.response.FailMessage.REGION_NOT_FOUND;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class RegionNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return REGION_NOT_FOUND.getMessage();
    }
}
