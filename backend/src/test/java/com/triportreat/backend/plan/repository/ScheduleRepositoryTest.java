package com.triportreat.backend.plan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    SchedulePlaceRepository schedulePlaceRepository;

    @Test
    @DisplayName("스케줄과 스케줄-장소 fetch join 테스트")
    void findByIdWithSchedulePlacesFetchJoin() {
        // given
        List<SchedulePlace> schedulePlaces = List.of(
                SchedulePlace.builder()
                        .visitOrder(1)
                        .memo("memo")
                        .expense(1000L)
                        .build(),
                SchedulePlace.builder()
                        .visitOrder(2)
                        .memo("memo2")
                        .expense(2000L)
                        .build());
        schedulePlaceRepository.saveAll(schedulePlaces);

        Schedule schedule = Schedule.builder()
                .schedulePlaces(schedulePlaces)
                .visitDate(LocalDate.now())
                .build();
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // when
        Optional<Schedule> scheduleWithSchedulePlaces = scheduleRepository.findByIdWithSchedulePlacesFetchJoin(savedSchedule.getId());

        // then
        assertThat(scheduleWithSchedulePlaces).isPresent();
        assertThat(scheduleWithSchedulePlaces.get().getSchedulePlaces().size()).isEqualTo(2);
        assertThat(scheduleWithSchedulePlaces.get().getSchedulePlaces().get(0).getVisitOrder()).isEqualTo(1);
        assertThat(scheduleWithSchedulePlaces.get().getSchedulePlaces().get(1).getVisitOrder()).isEqualTo(2);
    }

}