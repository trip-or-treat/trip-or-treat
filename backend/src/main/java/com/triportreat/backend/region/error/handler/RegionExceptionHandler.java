package com.triportreat.backend.region.error.handler;

import com.triportreat.backend.common.AbstractException;
import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.region.controller.RegionController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = RegionController.class)
public class RegionExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<?> usernameNotFoundExceptionHandler(AbstractException e) {
        return ResponseEntity.badRequest()
                .body(ResponseResult.fail(e.getMessage(), e.getStatus(), null));
    }
}
