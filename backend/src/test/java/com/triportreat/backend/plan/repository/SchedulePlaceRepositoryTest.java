package com.triportreat.backend.plan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SchedulePlaceRepositoryTest extends DummyObject {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    SchedulePlaceRepository schedulePlaceRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PlanRepository planRepository;

    @Test
    @DisplayName("스케줄-장소ID, 유저ID로 fetch join 테스트")
    void findByIdAndUserId() {
        // given
        Long id = 1L;
        User mockUser = createMockUser(id, "user");
        userRepository.save(mockUser);

        Plan mockPlan = createMockPlan(mockUser);
        planRepository.save(mockPlan);

        Schedule mockSchedule = createMockSchedule(mockPlan);
        scheduleRepository.save(mockSchedule);

        SchedulePlace mockSchedulePlace = createMockSchedulePlace(mockSchedule);
        schedulePlaceRepository.save(mockSchedulePlace);

        // when
        Optional<SchedulePlace> findByFetchJoin = schedulePlaceRepository
                .findByIdAndUserId(mockSchedulePlace.getId(), mockUser.getId());

        // then
        assertThat(findByFetchJoin).isNotEmpty();
        assertThat(findByFetchJoin.get().getId()).isEqualTo(mockSchedulePlace.getId());
        assertThat(findByFetchJoin.get().getSchedule().getPlan().getUser().getId()).isEqualTo(mockUser.getId());
    }
}