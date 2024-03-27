package com.triportreat.backend.plan.repository;

import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_FALSE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_TRUE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.SEARCH_TYPE_REGION;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triportreat.backend.common.config.JpaConfig;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.triportreat.backend.plan.entity.PlanRegion;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@Import(JpaConfig.class)
class PlanRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    PlanRegionRepository planRegionRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {
        autoIncrementReset();
        dataSetting();
        em.clear();
    }

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

    @Nested
    @DisplayName("제목 검색")
    class SearchByTitle {

        @Test
        @DisplayName("기본 검색값으로 검색")
        void planBasicSearchByTitle() {
            // given
            Long userId = 1L;
            PlanSearchRequestDto condition = new PlanSearchRequestDto();
            Pageable pageable = PageRequest.of(0, 10);

            // when
            Page<PlanListResponseDto> response = planRepository.searchPlans(condition, pageable, userId);

            // then
            assertThat(response.getTotalElements()).isEqualTo(50);
            assertThat(response.getContent().size()).isEqualTo(10);
            assertThat(response.getNumber()).isEqualTo(0);
            assertThat(response.getTotalPages()).isEqualTo(5);
        }

        @Test
        @DisplayName("제목에 '1'이 포함된 최근 6개월안에 작성된 지난 여행 계획 목록 검색")
        void searchPastPlanByTitleWithKeyword() {
            // given
            Long userId = 1L;
            PlanSearchRequestDto condition = PlanSearchRequestDto.builder()
                    .keyword("1")
                    .comingYn(COMING_YN_FALSE)
                    .build();
            Pageable pageable = PageRequest.of(0, 10);

            // when
            Page<PlanListResponseDto> response = planRepository.searchPlans(condition, pageable, userId);

            // then
            assertThat(response.getTotalElements()).isEqualTo(14);
            assertThat(response.getContent().size()).isEqualTo(10);
            assertThat(response.getNumber()).isEqualTo(0);
            assertThat(response.getTotalPages()).isEqualTo(2);
        }

        @Test
        @DisplayName("제목에 '1'이 포함된 최근 6개월안에 작성된 다가오는 여행 계획 목록 검색")
        void searchComingPlanByTitleWithKeyword() {
            // given
            Long userId = 1L;
            PlanSearchRequestDto condition = PlanSearchRequestDto.builder()
                    .keyword("1")
                    .comingYn(COMING_YN_TRUE)
                    .build();
            Pageable pageable = PageRequest.of(0, 10);

            // when
            Page<PlanListResponseDto> response = planRepository.searchPlans(condition, pageable, userId);

            // then
            assertThat(response.getTotalElements()).isEqualTo(6);
            assertThat(response.getContent().size()).isEqualTo(6);
            assertThat(response.getNumber()).isEqualTo(0);
            assertThat(response.getTotalPages()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("지역 검색")
    class SearchByRegion {
        @Test
        @DisplayName("지역에 인천이 포함된 최근 6개월안에 작성한 다가오는 여행 계획 목록 검색")
        void searchComingPlanByRegionWithKeyword() {
            // given
            Long userId = 1L;
            PlanSearchRequestDto condition = PlanSearchRequestDto.builder()
                    .type(SEARCH_TYPE_REGION)
                    .keyword("인천")
                    .comingYn(COMING_YN_TRUE)
                    .build();
            Pageable pageable = PageRequest.of(2, 10);

            // when
            Page<PlanListResponseDto> response = planRepository.searchPlans(condition, pageable, userId);

            // then
            assertThat(response.getTotalElements()).isEqualTo(50);
            assertThat(response.getContent().size()).isEqualTo(10);
            assertThat(response.getNumber()).isEqualTo(2);
            assertThat(response.getTotalPages()).isEqualTo(5);
        }

        @Test
        @DisplayName("지역에 인천이 포함된 올해에 작성한 다가오는 여행 계획 목록 검색")
        void searchComingPlanByRegionWithKeywordThisYear() {
            // given
            Long userId = 1L;
            PlanSearchRequestDto condition = PlanSearchRequestDto.builder()
                    .type(SEARCH_TYPE_REGION)
                    .keyword("인천")
                    .comingYn(COMING_YN_TRUE)
                    .year(LocalDate.now().getYear())
                    .build();
            Pageable pageable = PageRequest.of(0, 10);

            // when
            Page<PlanListResponseDto> response = planRepository.searchPlans(condition, pageable, userId);

            // then
            assertThat(response.getTotalElements()).isEqualTo(50);
            assertThat(response.getContent().size()).isEqualTo(10);
            assertThat(response.getNumber()).isEqualTo(0);
            assertThat(response.getTotalPages()).isEqualTo(5);
        }

        @Test
        @DisplayName("지역에 대전이 포함된 최근 6개월안에 작성된 지난 여행 계획 목록 검색")
        void searchPastPlanByRegionWithKeyword() {
            // given
            Long userId = 1L;
            PlanSearchRequestDto condition = new PlanSearchRequestDto();
            condition.setType(SEARCH_TYPE_REGION);
            condition.setKeyword("대전");
            condition.setComingYn(COMING_YN_FALSE);
            Pageable pageable = PageRequest.of(2, 10);

            // when
            Page<PlanListResponseDto> response = planRepository.searchPlans(condition, pageable, userId);

            // then
            assertThat(response.getTotalElements()).isEqualTo(50);
            assertThat(response.getContent().size()).isEqualTo(10);
            assertThat(response.getNumber()).isEqualTo(2);
            assertThat(response.getTotalPages()).isEqualTo(5);
        }
    }

    private void autoIncrementReset() {
        em.createNativeQuery("ALTER TABLE PLAN ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE PLAN_REGION ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE \"USER\" ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    private void dataSetting() {
        Region region1 = Region.builder().id(1L).name("서울").latitude(1.1).longitude(1.1).build();
        Region region2 = Region.builder().id(2L).name("인천").latitude(1.1).longitude(1.1).build();
        Region region3 = Region.builder().id(3L).name("대전").latitude(1.1).longitude(1.1).build();
        regionRepository.save(region1);
        regionRepository.save(region2);
        regionRepository.save(region3);

        User user = User.builder()
                .name("user")
                .email("user@gmail.com")
                .nickname("user")
                .imageThumbnail("")
                .imageOrigin("")
                .build();
        userRepository.save(user);

        for (long i = 1; i <= 50; i++) {
            Plan plan = Plan.builder()
                    .title("계획" + i)
                    .user(user)
                    .startDate(LocalDate.now().minusDays(3))
                    .endDate(LocalDate.now().minusDays(1))
                    .schedules(null).build();
            planRepository.save(plan);

            planRegionRepository.save(PlanRegion.builder().plan(plan).region(region1).build());
            planRegionRepository.save(PlanRegion.builder().plan(plan).region(region3).build());
        }

        for (long i = 51; i <= 100; i++) {
            Plan plan = Plan.builder()
                    .title("계획" + i)
                    .user(user)
                    .startDate(LocalDate.now().plusDays(1))
                    .endDate(LocalDate.now().plusDays(3))
                    .schedules(null).build();
            planRepository.save(plan);

            planRegionRepository.save(PlanRegion.builder().plan(plan).region(region1).build());
            planRegionRepository.save(PlanRegion.builder().plan(plan).region(region2).build());
            planRegionRepository.save(PlanRegion.builder().plan(plan).region(region3).build());
        }
    }
}