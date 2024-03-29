package com.triportreat.backend.plan.service;

import com.triportreat.backend.plan.domain.PlanResponseDto.PlanDetailResponseDto;
import com.triportreat.backend.common.response.PageResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import org.springframework.data.domain.Pageable;

public interface PlanService {

    void validatePlanOwner(Long id, Long userId);

    void createPlan(PlanCreateRequestDto planCreateRequestDto, Long userId);

    PlanDetailResponseDto getPlanDetail(Long id, Long userId);

    void updatePlan(PlanUpdateRequestDto planUpdateRequestDto);

    PageResponseDto<PlanListResponseDto> searchPlans(PlanSearchRequestDto condition, Pageable pageable, Long userId);
}