package com.triportreat.backend.plan.service;

import com.triportreat.backend.plan.domain.PlanCreateRequestDto;

public interface PlanService {
    void createPlan(PlanCreateRequestDto planCreateRequestDto);
}