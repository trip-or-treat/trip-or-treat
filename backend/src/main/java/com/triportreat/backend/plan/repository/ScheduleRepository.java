package com.triportreat.backend.plan.repository;

import com.triportreat.backend.plan.entity.Schedule;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s "
            + "LEFT JOIN FETCH s.schedulePlaces sp "
            + "WHERE s.id = :id")
    Optional<Schedule> findByIdWithSchedulePlacesFetchJoin(Long id);

}