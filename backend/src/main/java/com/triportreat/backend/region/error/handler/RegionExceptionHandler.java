package com.triportreat.backend.region.error.handler;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.region.controller.RegionController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = RegionController.class)
public class RegionExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    protected ResponseEntity<?> usernameNotFoundExceptionHandler(RuntimeException e) {
//        return ResponseEntity.badRequest().body(ResponseResult.fail("에러가 발생하였습니다!", null));
//    }
}
