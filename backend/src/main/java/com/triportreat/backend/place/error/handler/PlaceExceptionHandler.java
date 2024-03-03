package com.triportreat.backend.place.error.handler;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.place.controller.PlaceController;
import com.triportreat.backend.place.error.handler.exception.ApiCallFailedException;
import com.triportreat.backend.place.error.handler.exception.ApiResponseParseException;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

import static com.triportreat.backend.common.response.FailMessage.GET_FAIL;
import static com.triportreat.backend.common.response.FailMessage.UNKNOWN_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackageClasses = PlaceController.class)
public class PlaceExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> unknownRuntimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.ok().body(ResponseResult.fail(UNKNOWN_ERROR.getMessage(), INTERNAL_SERVER_ERROR, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.ok().body(ResponseResult.fail(GET_FAIL.getMessage(), BAD_REQUEST, errors));
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    protected ResponseEntity<?> placeNotFoundExceptionHandler(PlaceNotFoundException e) {
        return ResponseEntity.ok()
                .body(ResponseResult.fail(e.getMessage(), e.getStatus(), null));
    }

    @ExceptionHandler(ApiCallFailedException.class)
    protected ResponseEntity<?> apiCallFailedExceptionHandler(ApiCallFailedException e) {
        return ResponseEntity.ok().body(ResponseResult.fail(e.getMessage(), e.getStatus(), null));
    }

    @ExceptionHandler(ApiResponseParseException.class)
    protected ResponseEntity<?> apiResponseParseExceptionHandler(ApiResponseParseException e) {
        return ResponseEntity.ok().body(ResponseResult.fail(e.getMessage(), e.getStatus(), null));
    }

}
