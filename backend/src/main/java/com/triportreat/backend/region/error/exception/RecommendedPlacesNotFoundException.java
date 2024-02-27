package com.triportreat.backend.region.error.exception;

import static com.triportreat.backend.common.response.ErrorMessage.RECOMMENDED_PLACE_EMPTY;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class RecommendedPlacesNotFoundException extends AbstractException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return RECOMMENDED_PLACE_EMPTY.getMessage();
    }
}
