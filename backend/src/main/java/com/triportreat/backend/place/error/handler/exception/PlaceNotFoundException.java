package com.triportreat.backend.place.error.handler.exception;

import com.triportreat.backend.common.AbstractException;
import com.triportreat.backend.common.response.FailMessage;
import org.springframework.http.HttpStatus;

public class PlaceNotFoundException extends AbstractException {

    private final Long id;

    public PlaceNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return FailMessage.PLACE_NOT_FOUND.getMessage() + id;
    }
}

