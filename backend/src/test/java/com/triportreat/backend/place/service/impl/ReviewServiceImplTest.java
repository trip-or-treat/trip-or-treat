package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.domain.ReviewUpdateRequestDto;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.error.handler.exception.ReviewNotBelongPlaceException;
import com.triportreat.backend.place.error.handler.exception.ReviewNotFoundException;
import com.triportreat.backend.place.error.handler.exception.UserNotFoundException;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.ReviewRepository;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.triportreat.backend.common.response.FailMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest extends DummyObject {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("리뷰 목록 조회")
    class GetReviewList{

        @Test
        @DisplayName("성공")
        public void getReviewList() {
            //given
            User user = createMockUser(1L, "testUser");
            Place place = Place.builder().id(1L).build();
            List<Review> reviews = List.of(createMockReview(1L, user, place), createMockReview(2L, user, place));

            when(reviewRepository.findByPlaceId(1L, Pageable.unpaged())).thenReturn(reviews);
            when(placeRepository.existsById(1L)).thenReturn(true);

            //when
            List<ReviewListDto> reviewList = reviewService.getReviewList(1L, Pageable.unpaged());

            //then
            assertThat(reviewList).hasSize(2);
            assertThat(reviewList.get(0).getId()).isEqualTo(1L);
            assertThat(reviewList.get(0).getNickname()).isEqualTo("testUser");
            assertThat(reviewList.get(0).getImageThumbnail()).isEqualTo("");
            assertThat(reviewList.get(0).getContent()).isEqualTo("testContent");
            assertThat(reviewList.get(0).getTip()).isEqualTo("testTip");
            assertThat(reviewList.get(0).getScore()).isEqualTo(5);
            assertThat(reviewList.get(1).getId()).isEqualTo(2L);
        }

        @Test
        @DisplayName("실패 - 장소 정보가 없을시 예외 발생")
        public void  getReviewList_PlaceNotFoundException() {
            //given
            User user = createMockUser(1L, "testUser");
            Place place = Place.builder().id(1L).build();
            List<Review> reviews = List.of(createMockReview(1L, user, place), createMockReview(2L, user, place));

            when(reviewRepository.findByPlaceId(1L, Pageable.unpaged())).thenReturn(reviews);
            when(placeRepository.existsById(1L)).thenReturn(false);

            //when & then
            assertThatThrownBy(() -> reviewService.getReviewList(1L, Pageable.unpaged()))
                    .isInstanceOf(PlaceNotFoundException.class)
                    .hasMessageContaining(PLACE_NOT_FOUND.getMessage() + place.getId());
        }
    }
    
    @Nested
    @DisplayName("리뷰 저장")
    class CreateReview{
        @Test
        @DisplayName("성공")
        public void createReview() {
            //given
            User user = createMockUser(1L, "testUser");
            Place place = Place.builder().id(1L).build();
            ReviewRequestDto reviewRequestDto = createReviewRequestDto(user.getId(), place.getId());

            when(placeRepository.findById(1L)).thenReturn(Optional.of(place));
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));

            //when
            reviewService.createReview(reviewRequestDto);

            //then
            verify(reviewRepository).save(any(Review.class));
        }

        @Test
        @DisplayName("실패 - 장소 정보가 없을시 없을시 예외발생")
        public void createReview_PlaceNotFoundException() {
            //given
            User user = createMockUser(1L, "testUser");
            Place place = Place.builder().id(1L).build();
            ReviewRequestDto reviewRequestDto = createReviewRequestDto(user.getId(), place.getId());

            when(placeRepository.findById(1L)).thenReturn(Optional.empty());

            //when & then
            assertThatThrownBy(() -> reviewService.createReview(reviewRequestDto))
                    .isInstanceOf(PlaceNotFoundException.class)
                    .hasMessageContaining(PLACE_NOT_FOUND.getMessage() + place.getId());
        }

        @Test
        @DisplayName("실패 - 유저 정보가 없을시 예외발생")
        public void createReview_UserNotFoundException() {
            //given
            User user = createMockUser(1L, "testUser");
            Place place = Place.builder().id(1L).build();
            ReviewRequestDto reviewRequestDto = createReviewRequestDto(user.getId(), place.getId());

            when(placeRepository.findById(1L)).thenReturn(Optional.of(place));
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            //when & then
            assertThatThrownBy(() -> reviewService.createReview(reviewRequestDto))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessageContaining(USER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("리뷰 수정")
    class UpdateReview {

        @Test
        @DisplayName("성공")
        public void updateReview() {

            //given
            User user = createMockUser(1L, "testUser");
            Place place = Place.builder().id(1L).build();
            Review review = createMockReview(1L, user, place);

            ReviewUpdateRequestDto reviewUpdateRequestDto = createReviewUpdateRequestDto(place.getId());

            when(placeRepository.findById(reviewUpdateRequestDto.getPlaceId())).thenReturn(Optional.of(place));
            when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

            //when
            reviewService.updateReview(review.getId(), reviewUpdateRequestDto);

            //then
            assertThat(review.getPlace().getId()).isEqualTo(1L);
            assertThat(review.getContent()).isEqualTo("newContent");
            assertThat(review.getTip()).isEqualTo("newTip");
            assertThat(review.getScore()).isEqualTo(1);
        }

        @Test
        @DisplayName("실패 - 장소 정보가 없을시 없을시 예외발생")
        public void updateReview_PlaceNotFoundException() {

            //given
            Place place = Place.builder().id(1L).build();
            ReviewUpdateRequestDto reviewUpdateRequestDto = createReviewUpdateRequestDto(place.getId());

            //when & then
            assertThatThrownBy(() -> reviewService.updateReview(place.getId(), reviewUpdateRequestDto))
                    .isInstanceOf(PlaceNotFoundException.class)
                    .hasMessageContaining(PLACE_NOT_FOUND.getMessage() + place.getId());
        }

        @Test
        @DisplayName("실패 - 수정할 리뷰가 존재하지 않을시 예외발생")
        public void updateReview_ReviewNotFoundException() {

            //given
            Place place = Place.builder().id(1L).build();
            ReviewUpdateRequestDto reviewUpdateRequestDto = createReviewUpdateRequestDto(place.getId());

            when(placeRepository.findById(reviewUpdateRequestDto.getPlaceId())).thenReturn(Optional.of(place));

            //when & then
            assertThatThrownBy(() -> reviewService.updateReview(place.getId(), reviewUpdateRequestDto))
                    .isInstanceOf(ReviewNotFoundException.class)
                    .hasMessageContaining(REVIEW_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("실패 - 리뷰가 요청된 장소에 속하지 않을시 예외발생")
        public void updateReview_ReviewNotBelongPlaceException() {

            //given
            Place place1 = Place.builder().id(1L).build();
            User user = createMockUser(1L, "testUser");
            Review review = createMockReview(1L, user, place1);

            //when
            Place place2 = Place.builder().id(2L).build();
            ReviewUpdateRequestDto reviewUpdateRequestDto = createReviewUpdateRequestDto(place2.getId());

            when(placeRepository.findById(reviewUpdateRequestDto.getPlaceId())).thenReturn(Optional.of(place2));
            when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

            //then
            assertThatThrownBy(() -> reviewService.updateReview(review.getId(), reviewUpdateRequestDto))
                    .isInstanceOf(ReviewNotBelongPlaceException.class)
                    .hasMessageContaining(REVIEW_NOT_BELONG_TO_PLACE.getMessage());
        }
    }

    @Nested
    @DisplayName("리뷰 삭제")
    class DeleteReview {

        @Test
        @DisplayName("성공")
        void deleteReview() {

            //given
            Long userId = 1L;
            Long id = 1L;
            User user = createMockUser(userId, "testUser");
            Place place = Place.builder().build();
            Review review = createMockReview(id, user, place);

            when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

            //when
            reviewService.deleteReview(userId, id);

            //then
            verify(reviewRepository).deleteById(id);
        }

        @Test
        @DisplayName("실패 - 리뷰가 존재하지 않을 때 예외 발생")
        void deleteReview_ReviewNotFoundException() {

            //given
            Long userId = 1L;
            Long id = 1L;

            when(reviewRepository.findById(id)).thenReturn(Optional.empty());

            //when & then
            assertThatThrownBy(() -> reviewService.deleteReview(userId,id))
                    .isInstanceOf(ReviewNotFoundException.class)
                    .hasMessage(REVIEW_NOT_FOUND.getMessage());

            verify(reviewRepository, never()).deleteById(anyLong());
        }

        @Test
        @DisplayName("실패 - 사용자 ID가 일치하지 않을 때 예외 발생")
        void deleteReview_AuthenticateFailException() {

            //given
            Long userId = 1L;
            Long wrongUserId = 2L;
            Long id = 1L;
            User user = createMockUser(userId, "testUser");
            Place place = Place.builder().build();
            Review review = createMockReview(id, user, place);

            when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

            //when & then
            assertThatThrownBy(() -> reviewService.deleteReview(wrongUserId, id))
                    .isInstanceOf(AuthenticateFailException.class)
                    .hasMessage(AUTHENTICATION_FAILED.getMessage());

            verify(reviewRepository, never()).deleteById(anyLong());
        }
    }
}