package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.plan.domain.PlanCreateRequestDto;
import com.triportreat.backend.plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping("/plans")
    public ResponseEntity<?> createPlan(@RequestBody @Valid PlanCreateRequestDto planCreateRequestDto) {
        planService.createPlan(planCreateRequestDto);
        return ResponseEntity.ok().body(ResponseResult.success(POST_SUCCESS.getMessage(), null));
    }

}
