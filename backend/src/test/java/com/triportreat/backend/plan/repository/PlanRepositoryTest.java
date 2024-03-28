package com.triportreat.backend.plan.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
class PlanRepositoryTest {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByIdAndUserId() {
        // given
        User user = User.builder()
                .name("user1")
                .nickname("user1")
                .email("user1@gmail.com")
                .imageOrigin("")
                .imageThumbnail("")
                .build();
        userRepository.save(user);

        Plan plan = Plan.builder()
                        .title("Plan")
                        .user(user)
                        .regions(null)
                        .schedules(null)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .build();
        planRepository.save(plan);

        // when
        Optional<Plan> foundPlan = planRepository.findByIdAndUserId(plan.getId(), user.getId());

        // then
        assertTrue(foundPlan.isPresent());
        assertThat(foundPlan.get().getUser().getName()).isEqualTo("user1");
        assertThat(foundPlan.get().getTitle()).isEqualTo("Plan");
    }
}