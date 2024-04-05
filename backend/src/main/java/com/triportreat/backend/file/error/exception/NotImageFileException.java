package com.triportreat.backend.file.error.exception;

import static com.triportreat.backend.common.response.FailMessage.NOT_IMAGE_FILE;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class NotImageFileException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
    }

    @Override
    public String getMessage() {
        return NOT_IMAGE_FILE.getMessage();
    }
}
