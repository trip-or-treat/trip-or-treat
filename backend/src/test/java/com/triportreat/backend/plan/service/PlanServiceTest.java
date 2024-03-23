package com.triportreat.backend.plan.service;

import static com.triportreat.backend.common.response.FailMessage.AUTHENTICATION_FAILED;
import static com.triportreat.backend.common.response.FailMessage.PLACE_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.PLAN_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.SCHEDULE_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.SCHEDULE_PLACE_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_FALSE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.SEARCH_TYPE_REGION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanDetailResponseDto;
import com.triportreat.backend.common.response.PageResponseDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanUpdateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.ScheduleCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanRequestDto.SchedulePlaceCreateRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.entity.PlanRegion;
import com.triportreat.backend.plan.entity.Schedule;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.plan.error.exception.PlaceNotFoundException;
import com.triportreat.backend.plan.error.exception.PlanNotFoundException;
import com.triportreat.backend.plan.error.exception.ScheduleNotFoundException;
import com.triportreat.backend.plan.error.exception.SchedulePlaceNotFoundException;
import com.triportreat.backend.plan.error.exception.UserNotFoundException;
import com.triportreat.backend.plan.repository.PlanRepository;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.repository.ScheduleRepository;
import com.triportreat.backend.plan.service.impl.PlanServiceImpl;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest extends DummyObject {

    @InjectMocks
    PlanServiceImpl planService;

    @Mock
    PlanRepository planRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    PlaceRepository placeRepository;

    @Mock
    SchedulePlaceRepository schedulePlaceRepository;

    @Nested
    @DisplayName("계획 저장")
    class CreatePlan {

        @Test
        @DisplayName("성공")
        void createPlan() {
            // given
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests1 = List.of(
                    createSchedulePlaceRequestDto(1L, 1),
                    createSchedulePlaceRequestDto(2L, 2));
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests2 = List.of(
                    createSchedulePlaceRequestDto(3L, 1),
                    createSchedulePlaceRequestDto(4L, 2));

            List<ScheduleCreateRequestDto> scheduleRequests = List.of(
                    createScheduleRequestDto(LocalDate.now(), schedulePlaceRequests1),
                    createScheduleRequestDto(LocalDate.now().plusDays(1), schedulePlaceRequests2));

            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDto(LocalDate.now(), LocalDate.now().plusDays(1), 1L, scheduleRequests);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(createMockUser(1L, "user1")));
            when(planRepository.save(any(Plan.class))).thenReturn(createMockPlan(1L));
            when(placeRepository.findById(anyLong())).thenReturn(Optional.of(Place.builder().id(1L).build()));

            // when
            planService.createPlan(planCreateRequestDto);

            // then
            verify(schedulePlaceRepository, times(4)).save(any(SchedulePlace.class));
            verify(scheduleRepository, times(2)).save(any(Schedule.class));
            verify(planRepository, times(1)).save(any(Plan.class));
        }

        @Test
        @DisplayName("실패 - 사용자가 존재하지 않을시 예외발생")
        void createPlan_UserNotFoundException() {
            // given
            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDto(LocalDate.now(), LocalDate.now().plusDays(1), 1L, null);

            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.createPlan(planCreateRequestDto))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("실패 - 선택한 장소가 존재하지 않을시 예외발생")
        void createPlan_PlaceNotFoundException() {
            // given
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests1 = List.of(
                    createSchedulePlaceRequestDto(1L, 1),
                    createSchedulePlaceRequestDto(2L, 2));
            List<SchedulePlaceCreateRequestDto> schedulePlaceRequests2 = List.of(
                    createSchedulePlaceRequestDto(3L, 1),
                    createSchedulePlaceRequestDto(4L, 2));

            List<ScheduleCreateRequestDto> scheduleRequests = List.of(
                    createScheduleRequestDto(LocalDate.now(), schedulePlaceRequests1),
                    createScheduleRequestDto(LocalDate.now().plusDays(1), schedulePlaceRequests2));

            PlanCreateRequestDto planCreateRequestDto = createPlanRequestDto(LocalDate.now(), LocalDate.now().plusDays(1), 1L, scheduleRequests);
            User user = createMockUser(planCreateRequestDto.getUserId(), "user1");
            Plan plan = createMockPlan(1L);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(planRepository.save(any(Plan.class))).thenReturn(plan);
            when(placeRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.createPlan(planCreateRequestDto))
                    .isInstanceOf(PlaceNotFoundException.class)
                    .hasMessage(PLACE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("계획 상세 조회")
    class GetPlanDetail {

        @Test
        @DisplayName("성공")
        void getPlanDetail_Success() {
            // given
            Region region1 = createMockRegion(1L, "서울");
            Region region2 = createMockRegion(2L, "인천");
            ContentType contentType = ContentType.builder().id(1L).name("관광지").build();
            SubCategory subCategory = SubCategory.builder().id("A1").name("산").build();
            Place place1 = createMockPlace(1L, region1, contentType, subCategory, "place1");
            Place place2 = createMockPlace(1L, region2, contentType, subCategory, "place2");
            Set<PlanRegion> regions = Set.of(createPlanRegion(1L, null, region1), createPlanRegion(2L, null, region2));

            List<SchedulePlace> schedulePlaces1 = List.of(
                    createMockSchedulePlace(1L, place1, null, 1),
                    createMockSchedulePlace(2L, place2, null, 2));
            List<SchedulePlace> schedulePlaces2 = List.of(
                    createMockSchedulePlace(3L, place1, null, 3),
                    createMockSchedulePlace(4L, place2, null, 4));
            List<Schedule> schedules = List.of(
                    createMockSchedule(1L, null, schedulePlaces1, LocalDate.now()),
                    createMockSchedule(2L, null, schedulePlaces2, LocalDate.now().plusDays(1)));
            User user = createMockUser(1L, "user1");
            Plan plan = createMockPlan(1L, "계획", user, regions, schedules, null, null);

            when(planRepository.findByIdAndUserId(anyLong(), any())).thenReturn(Optional.of(plan));

            // when
            PlanDetailResponseDto planDetail = planService.getPlanDetail(1L, 1L);

            // then
            assertThat(planDetail.getPlanId()).isEqualTo(1L);
            assertThat(planDetail.getSchedules().size()).isEqualTo(2);
            assertThat(planDetail.getSchedules().get(0).getSchedulePlaces().size()).isEqualTo(2);
            assertThat(planDetail.getSchedules().get(1).getSchedulePlaces().size()).isEqualTo(2);
            assertThat(planDetail.getRegions().getRegionIds().size()).isEqualTo(2);
            assertThat(planDetail.getRegions().getRegionNames().size()).isEqualTo(2);
        }

        @Test
        @DisplayName("실패 - 계획 데이터 없음")
        void getPlanDetail_PlanNotFound() {
            // given
            Long planId = 1L;
            Long userId = 1L;

            when(planRepository.findByIdAndUserId(anyLong(), any())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.getPlanDetail(planId, userId))
                    .isInstanceOf(AuthenticateFailException.class)
                    .hasMessage(AUTHENTICATION_FAILED.getMessage());
        }
    }

    @Nested
    @DisplayName("계획 수정")
    class UpdatePlan {

        @Test
        @DisplayName("성공")
        void updatePlan() {
            // given
            Long id = 1L;
            Long userId = 1L;
            PlanUpdateRequestDto mockUpdateRequestDto = createPlanUpdateRequestDto();
            mockUpdateRequestDto.setPlanId(id);
            mockUpdateRequestDto.setUserId(userId);

            SchedulePlace mockSchedulePlace = createMockSchedulePlace(1L, Place.builder().id(1L).build(), null, 1);
            List<SchedulePlace> mockSchedulePlaces = new ArrayList<>();
            mockSchedulePlaces.add(mockSchedulePlace);

            Schedule mockSchedule = createMockSchedule(1L,null, mockSchedulePlaces, LocalDate.now());
            List<Schedule> mockSchedules = new ArrayList<>();
            mockSchedules.add(mockSchedule);

            Plan mockPlan = createMockPlan(1L, "계획", null, null, mockSchedules, null, null);

            when(planRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
            when(planRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockPlan));
            when(scheduleRepository.findByIdWithSchedulePlacesFetchJoin(anyLong())).thenReturn(
                    Optional.of(mockSchedule));
            when(schedulePlaceRepository.findById(anyLong())).thenReturn(Optional.of(mockSchedulePlace));
            when(placeRepository.findById(anyLong())).thenReturn(Optional.of(Place.builder().id(1L).build()));
            when(schedulePlaceRepository.save(any(SchedulePlace.class))).thenReturn(createMockSchedulePlace(2L, Place.builder().id(2L).build(), null, 2));

            // when
            planService.updatePlan(mockUpdateRequestDto);

            // then
            verify(planRepository, times(1)).existsByIdAndUserId(anyLong(), anyLong());
            verify(planRepository, times(1)).findById(anyLong());
            verify(scheduleRepository, times(1)).findByIdWithSchedulePlacesFetchJoin(anyLong());
            verify(schedulePlaceRepository, times(1)).findById(anyLong());
            verify(placeRepository, times(1)).findById(anyLong());
            verify(schedulePlaceRepository, times(1)).save(any(SchedulePlace.class));

            assertThat(planRepository.findById(id).get().getTitle()).isEqualTo(mockUpdateRequestDto.getTitle());
        }

        @Test
        @DisplayName("실패 - 사용자ID와 계획ID로 일치하는 계획 정보 없음")
        void updatePlan_AuthenticationFailed() {
            // given
            Long id = 2L;
            Long userId = 1L;
            PlanUpdateRequestDto mockRequestDto = createPlanUpdateRequestDto();
            mockRequestDto.setUserId(userId);

            when(planRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(false);

            // when
            // then
            assertThatThrownBy(() -> planService.validatePlanOwner(id, userId))
                    .isInstanceOf(AuthenticateFailException.class)
                    .hasMessage(AUTHENTICATION_FAILED.getMessage());
        }

        @Test
        @DisplayName("실패 - 계획 정보 없음")
        void updatePlan_PlanNotFound() {
            // given
            Long id = 100L;
            Long userId = 1L;
            PlanUpdateRequestDto mockRequestDto = createPlanUpdateRequestDto();
            mockRequestDto.setPlanId(id);
            mockRequestDto.setUserId(userId);

            when(planRepository.findById(anyLong())).thenReturn(Optional.empty());
            // when
            // then
            assertThatThrownBy(() -> planService.updatePlan(mockRequestDto))
                    .isInstanceOf(PlanNotFoundException.class)
                    .hasMessage(PLAN_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("실패 - 스케쥴 정보 없음")
        void updatePlan_ScheduleNotFound() {
            // given
            Long id = 1L;
            Long userId = 1L;
            PlanUpdateRequestDto mockRequestDto = createPlanUpdateRequestDto();
            mockRequestDto.setPlanId(id);
            mockRequestDto.setUserId(userId);

            when(planRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
            when(planRepository.findById(anyLong())).thenReturn(Optional.of(createMockPlan(1L)));
            when(scheduleRepository.findByIdWithSchedulePlacesFetchJoin(anyLong())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.updatePlan(mockRequestDto))
                    .isInstanceOf(ScheduleNotFoundException.class)
                    .hasMessage(SCHEDULE_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("실패 - 스케줄-장소 정보 없음")
        void updatePlan_SchedulePlaceNotFound() {
            // given
            Long id = 1L;
            Long userId = 1L;
            PlanUpdateRequestDto mockUpdateRequestDto = createPlanUpdateRequestDto();
            mockUpdateRequestDto.setPlanId(id);
            mockUpdateRequestDto.setUserId(userId);

            SchedulePlace mockSchedulePlace = createMockSchedulePlace(1L, Place.builder().id(1L).build(), null, 1);
            List<SchedulePlace> mockSchedulePlaces = new ArrayList<>();
            mockSchedulePlaces.add(mockSchedulePlace);

            Schedule mockSchedule = createMockSchedule(1L, null, mockSchedulePlaces, LocalDate.now());
            List<Schedule> mockSchedules = new ArrayList<>();
            mockSchedules.add(mockSchedule);

            Plan mockPlan = createMockPlan(1L, "계획", null, null, mockSchedules, null, null);

            when(planRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
            when(planRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockPlan));
            when(scheduleRepository.findByIdWithSchedulePlacesFetchJoin(anyLong())).thenReturn(
                    Optional.of(mockSchedule));
            when(schedulePlaceRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> planService.updatePlan(mockUpdateRequestDto))
                    .isInstanceOf(SchedulePlaceNotFoundException.class)
                    .hasMessage(SCHEDULE_PLACE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("내 계획 목록 검색 및 조회")
    class SearchPlans {

        @Test
        @DisplayName("성공")
        void searchPlans_Success() {
            // given
            List<PlanListResponseDto> content = List.of(
                    PlanListResponseDto.builder()
                            .planId(1L)
                            .title("계획1")
                            .regions(List.of("서울", "인천", "대전"))
                            .startDate(LocalDate.now().minusDays(3))
                            .endDate(LocalDate.now().minusDays(1))
                            .createdDate(LocalDateTime.now().minusDays(7))
                            .build(),
                    PlanListResponseDto.builder()
                            .planId(2L)
                            .title("계획2")
                            .regions(List.of("서울", "인천", "대전"))
                            .startDate(LocalDate.now().minusDays(3))
                            .endDate(LocalDate.now().minusDays(1))
                            .createdDate(LocalDateTime.now().minusDays(7))
                            .build());

            Pageable pageable = PageRequest.of(0, 10);
            long total = 2L;
            PageImpl<PlanListResponseDto> response = new PageImpl<>(content, pageable, total);

            PlanSearchRequestDto condition = PlanSearchRequestDto.builder()
                    .type(SEARCH_TYPE_REGION)
                    .keyword("서울")
                    .comingYn(COMING_YN_FALSE)
                    .build();

            Long userId = 1L;

            when(userRepository.existsById(anyLong())).thenReturn(true);
            when(planRepository.searchPlans(any(), any(), any())).thenReturn(response);

            // when
            PageResponseDto<PlanListResponseDto> pageResponse = planService.searchPlans(condition, pageable, userId);

            // then
            assertThat(pageResponse.getContents().size()).isEqualTo(2);
            assertThat(pageResponse.getPage()).isEqualTo(1);
            assertThat(pageResponse.getTotalPages()).isEqualTo(1);
            assertThat(pageResponse.getTotalElements()).isEqualTo(2);
            assertThat(pageResponse.getPrev()).isEqualTo(false);
            assertThat(pageResponse.getNext()).isEqualTo(false);
            assertThat(pageResponse.getFirst()).isEqualTo(true);
            assertThat(pageResponse.getLast()).isEqualTo(true);
            assertThat(pageResponse.getStartPage()).isEqualTo(1);
            assertThat(pageResponse.getEndPage()).isEqualTo(1);
        }

        @Test
        @DisplayName("실패 - 사용자가 존재하지 않음")
        void searchPlans_UserNotExist() {
            // given
            Pageable pageable = PageRequest.of(0, 10);

            PlanSearchRequestDto condition = PlanSearchRequestDto.builder()
                    .type(SEARCH_TYPE_REGION)
                    .keyword("서울")
                    .comingYn(COMING_YN_FALSE)
                    .build();

            when(userRepository.existsById(anyLong())).thenReturn(false);

            // when
            // then
            assertThatThrownBy(() -> planService.searchPlans(condition, pageable, 1L))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(USER_NOT_FOUND.getMessage());
        }
    }
}