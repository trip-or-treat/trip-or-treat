package com.triportreat.backend.plan.error.handler;

import static com.triportreat.backend.common.response.FailMessage.VALIDATION_FAILED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.triportreat.backend.common.AbstractException;
import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.plan.controller.PlanController;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = PlanController.class)
public class PlanExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.ok().body(ResponseResult.fail(VALIDATION_FAILED.getMessage(), BAD_REQUEST, errors));
    }

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<?> planAbstractExceptionHandler(AbstractException e) {
        return ResponseEntity.ok().body(ResponseResult.fail(e.getMessage(), e.getStatus(), null));
    }
}
