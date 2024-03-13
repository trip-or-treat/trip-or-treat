package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping("/plans/{id}")
    public ResponseEntity<?> getPlanDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ResponseResult.success(GET_SUCCESS.getMessage(), planService.getPlanDetail(id)));
    }

    @PostMapping("/plans")
    public ResponseEntity<?> createPlan(@RequestBody @Valid PlanCreateRequestDto planCreateRequestDto) {
        planCreateRequestDto.setUserId(1L); // TODO 로그인 기능 구현 완료시 수정 필요
        planService.createPlan(planCreateRequestDto);
        return ResponseEntity.ok().body(ResponseResult.success(POST_SUCCESS.getMessage(), null));
    }

}
