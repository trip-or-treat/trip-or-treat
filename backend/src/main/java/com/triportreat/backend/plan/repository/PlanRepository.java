package com.triportreat.backend.plan.repository;

import com.triportreat.backend.plan.entity.Plan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    boolean existsByIdAndUserId(Long id, Long userId);

    Optional<Plan> findByIdAndUserId(Long planId, Long userId);

}