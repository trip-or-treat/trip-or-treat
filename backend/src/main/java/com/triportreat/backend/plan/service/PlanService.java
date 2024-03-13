package com.triportreat.backend.plan.service;

import com.triportreat.backend.plan.domain.PlanDetailResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;

public interface PlanService {
    void createPlan(PlanCreateRequestDto planCreateRequestDto);

    PlanDetailResponseDto getPlanDetail(Long id);
}