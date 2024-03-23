package com.triportreat.backend.plan.service;

import static com.triportreat.backend.common.response.FailMessage.AUTHENTICATION_FAILED;
import static com.triportreat.backend.common.response.FailMessage.SCHEDULE_PLACE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.plan.entity.SchedulePlace;
import com.triportreat.backend.plan.error.exception.SchedulePlaceNotFoundException;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.service.impl.SchedulePlaceServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SchedulePlaceServiceTest extends DummyObject {

    @InjectMocks
    SchedulePlaceServiceImpl schedulePlaceService;

    @Mock
    SchedulePlaceRepository schedulePlaceRepository;

    @Nested
    @DisplayName("스케줄-장소 소유자 검증")
    class ValidateSchedulePlaceOwner {

        @Test
        @DisplayName("성공")
        void validateSchedulePlaceOwner() {
            // given
            Long userId = 1L;
            Long id = 1L;
            SchedulePlace mockSchedulePlace = createMockSchedulePlace(1L, 123L, 1);

            // when
            when(schedulePlaceRepository.findById(id)).thenReturn(Optional.ofNullable(mockSchedulePlace));
            when(schedulePlaceRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.ofNullable(mockSchedulePlace));

            // then
            assertDoesNotThrow(() -> schedulePlaceService.validateSchedulePlaceOwner(userId, id));
        }

        @Test
        @DisplayName("실패 - 스케줄-장소가 존재하지 않으면 예외 발생")
        void validateSchedulePlaceOwner_SchedulePlaceNotFound() {
            // given
            Long userId = 1L;
            Long id = 1L;
            SchedulePlace mockSchedulePlace = createMockSchedulePlace(10L, 123L, 1);

            // when
            when(schedulePlaceRepository.findById(id)).thenReturn(Optional.empty());

            // then
            assertThatThrownBy(() -> schedulePlaceService.validateSchedulePlaceOwner(userId, id))
                    .isInstanceOf(SchedulePlaceNotFoundException.class)
                    .hasMessageContaining(SCHEDULE_PLACE_NOT_FOUND.getMessage());

        }

        @Test
        @DisplayName("실패 - 스케줄-장소 소유자가 아니면 예외 발생")
        void validateSchedulePlaceOwner_AuthenticateFail() {
            // given
            Long userId = 2L;
            Long id = 1L;
            SchedulePlace mockSchedulePlace = createMockSchedulePlace(1L, 123L, 1);

            // when
            when(schedulePlaceRepository.findById(id)).thenReturn(Optional.ofNullable(mockSchedulePlace));
            when(schedulePlaceRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.empty());

            // then
            assertThatThrownBy(() -> schedulePlaceService.validateSchedulePlaceOwner(userId, id))
                    .isInstanceOf(AuthenticateFailException.class)
                    .hasMessageContaining(AUTHENTICATION_FAILED.getMessage());
        }
    }

    @Nested
    @DisplayName("스케줄-장소 삭제")
    class DeleteSchedulePlace {

        @Test
        @DisplayName("성공")
        void deleteSchedulePlace() {
            // given
            Long id = 1L;
            SchedulePlace mockSchedulePlace = createMockSchedulePlace(1L, 123L, 1);

            // when
            schedulePlaceRepository.deleteById(id);

            // then
            verify(schedulePlaceRepository).deleteById(id);
        }
    }

}