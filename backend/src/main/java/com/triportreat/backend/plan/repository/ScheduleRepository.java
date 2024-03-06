package com.triportreat.backend.plan.repository;

import com.triportreat.backend.plan.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}