package com.triportreat.backend.place.error.handler.exception;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

import static com.triportreat.backend.common.response.FailMessage.API_CALL_FAILED;

public class ApiCallFailedException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return API_CALL_FAILED.getMessage();
    }
}
