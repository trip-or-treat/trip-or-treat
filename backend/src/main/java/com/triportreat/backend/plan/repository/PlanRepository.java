package com.triportreat.backend.plan.repository;

import com.triportreat.backend.plan.entity.Plan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    boolean existsByIdAndUserId(Long id, Long userId);

    @Query("SELECT p FROM Plan p "
            + "LEFT JOIN FETCH p.schedules s "
            + "WHERE p.id = :id")
    Optional<Plan> findByIdWithSchedulesFetchJoin(Long id);

}