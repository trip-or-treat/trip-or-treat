package com.triportreat.backend.user.error.exception;

import com.triportreat.backend.common.AbstractException;
import org.springframework.http.HttpStatus;

import static com.triportreat.backend.common.response.FailMessage.USER_NICKNAME_EXIST;

public class UserNicknameExistException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return USER_NICKNAME_EXIST.getMessage();
    }
}
