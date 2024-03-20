package com.triportreat.backend.plan.repository;

import com.triportreat.backend.plan.entity.SchedulePlace;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SchedulePlaceRepository extends JpaRepository<SchedulePlace, Long> {

    @Query("SELECT sp FROM SchedulePlace sp "
            + "LEFT JOIN FETCH sp.schedule s "
            + "LEFT JOIN FETCH s.plan p "
            + "WHERE sp.id = :id AND p.user.id = :userId")
    Optional<SchedulePlace> findByIdAndUserId(Long id, Long userId);

}
