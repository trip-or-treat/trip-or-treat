package com.triportreat.backend.common;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {
    abstract public HttpStatus getStatus();
    abstract public String getMessage();
}
