package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.PATCH_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;
import static com.triportreat.backend.plan.domain.PlanRequestDto.PlanUpdateRequestDto;

import com.triportreat.backend.auth.utils.Auth;
import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlanDetail(@Auth Long userId, @PathVariable("id") Long planId) {
        return ResponseEntity.ok().body(ResponseResult.success(GET_SUCCESS.getMessage(), planService.getPlanDetail(planId, userId)));
    }

    @GetMapping
    public ResponseEntity<?> searchPlans(@Auth Long userId, PlanSearchRequestDto condition, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(ResponseResult.success(GET_SUCCESS.getMessage(), planService.searchPlans(condition, pageable, userId)));
    }

    @PostMapping
    public ResponseEntity<?> createPlan(@Auth Long userId, @RequestBody @Valid PlanCreateRequestDto planCreateRequestDto) {
        planService.createPlan(planCreateRequestDto, userId);
        return ResponseEntity.ok().body(ResponseResult.success(POST_SUCCESS.getMessage(), null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePlan(@Auth Long userId,
                                        @PathVariable Long id,
                                        @RequestBody @Valid PlanUpdateRequestDto planUpdateRequestDto) {
        planUpdateRequestDto.setUserId(userId);
        planUpdateRequestDto.setPlanId(id);
        planService.updatePlan(planUpdateRequestDto);
        return ResponseEntity.ok().body(ResponseResult.success(PATCH_SUCCESS.getMessage(), null));
    }

}
