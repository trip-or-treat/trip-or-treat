package com.triportreat.backend.plan.repository;

import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanRepositoryCustom {
    Page<PlanListResponseDto> searchPlans(PlanSearchRequestDto condition, Pageable pageable, Long userId);
}
