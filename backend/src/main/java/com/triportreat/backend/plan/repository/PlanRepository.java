package com.triportreat.backend.plan.repository;

import com.triportreat.backend.plan.entity.Plan;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {

    boolean existsByIdAndUserId(Long id, Long userId);

    @EntityGraph(attributePaths = {"schedules"})
    Optional<Plan> findByIdAndUserId(Long planId, Long userId);

    Optional<Plan> findByCode(String code);

}