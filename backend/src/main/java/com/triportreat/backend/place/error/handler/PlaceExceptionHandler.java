package com.triportreat.backend.place.error.handler;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.place.controller.PlaceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = PlaceController.class)
public class PlaceExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> unknownRuntimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(ResponseResult.fail("에러가 발생하였습니다!", HttpStatus.INTERNAL_SERVER_ERROR, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> regionIdIsNullExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ResponseResult.fail("지역ID는 null이 될 수 없습니다.", HttpStatus.BAD_REQUEST, null));
    }
}
