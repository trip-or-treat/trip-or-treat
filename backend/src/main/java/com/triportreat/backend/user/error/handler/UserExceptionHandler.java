package com.triportreat.backend.user.error.handler;

import com.triportreat.backend.common.AbstractException;
import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.user.controller.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

import static com.triportreat.backend.common.response.FailMessage.VALIDATION_FAILED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<?> UserAbstractExceptionHandler(AbstractException e) {
        return ResponseEntity.ok().body(ResponseResult.fail(e.getMessage(), e.getStatus(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.ok().body(ResponseResult.fail(VALIDATION_FAILED.getMessage(), BAD_REQUEST, errors));
    }
}
