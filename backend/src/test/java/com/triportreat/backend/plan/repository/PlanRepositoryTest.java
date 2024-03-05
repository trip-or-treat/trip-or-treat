package com.triportreat.backend.plan.repository;

import static org.assertj.core.api.Assertions.*;

import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PlanRepositoryTest {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("계획엔티티 save() 테스트")
    void savePlan() {
        // given
        LocalDate today = LocalDate.now();
        LocalDate nextDay = today.plusDays(1);

        Plan plan = Plan.builder()
                .title("title")
                .startDate(today)
                .endDate(nextDay)
                .build();
        planRepository.save(plan);

        Schedule schedule1 = Schedule.builder()
                .plan(plan)
                .visitDate(today)
                .build();
        scheduleRepository.save(schedule1);

        Schedule schedule2 = Schedule.builder()
                .plan(plan)
                .visitDate(nextDay)
                .build();
        scheduleRepository.save(schedule2);

        // when
        List<Schedule> schedules = scheduleRepository.findAll();

        // then
        Schedule savedSchedule1 = schedules.get(0);
        Schedule savedSchedule2 = schedules.get(1);

        assertThat(schedules.size()).isEqualTo(2);
        assertThat(savedSchedule1.getVisitDate()).isEqualTo(today);
        assertThat(savedSchedule1.getPlan()).usingRecursiveComparison().isEqualTo(plan);
        assertThat(savedSchedule2.getPlan()).usingRecursiveComparison().isEqualTo(plan);
    }
}