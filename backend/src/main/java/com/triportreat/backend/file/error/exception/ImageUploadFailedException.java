package com.triportreat.backend.file.error.exception;

import static com.triportreat.backend.common.response.FailMessage.IMAGE_UPLOAD_FAILED;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

public class ImageUploadFailedException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return IMAGE_UPLOAD_FAILED.getMessage();
    }
}
