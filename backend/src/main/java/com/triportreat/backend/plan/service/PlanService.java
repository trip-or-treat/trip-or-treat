package com.triportreat.backend.plan.service;

import com.triportreat.backend.plan.domain.PlanDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanUpdateRequestDto;

public interface PlanService {

    void validatePlanOwner(Long id, Long userId);

    void createPlan(PlanCreateRequestDto planCreateRequestDto);

    PlanDetailResponseDto getPlanDetail(Long id);

    void updatePlan(PlanUpdateRequestDto planUpdateRequestDto);
}