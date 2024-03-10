package com.triportreat.backend.plan.service;

import com.triportreat.backend.plan.domain.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanDetailResponseDto;

public interface PlanService {
    void createPlan(PlanCreateRequestDto planCreateRequestDto);

    PlanDetailResponseDto getPlanDetail(Long id);
}