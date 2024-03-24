package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.place.domain.MyReviewListDto;
import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
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

import static com.triportreat.backend.common.response.FailMessage.PLACE_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @DisplayName("내 리뷰 목록 조회")
    class GetMyReviewList {

        @Test
        @DisplayName("성공")
        public void getMyReviewList() {

            //given
            User user = createMockUser(1L, "testUser");
            Place place1 = Place.builder().name("place1").build();
            Place place2 = Place.builder().name("place2").build();
            List<Review> reviews = List.of(createMockReview(1L, user, place1), createMockReview(2L, user, place2));

            when(userRepository.existsById(user.getId())).thenReturn(true);
            when(reviewRepository.findByUserId(user.getId(), Pageable.unpaged())).thenReturn(reviews);

            //when
            List<MyReviewListDto> myReviewList = reviewService.getMyReviewList(user.getId(), Pageable.unpaged());

            //then
            assertThat(myReviewList).hasSize(2);
            assertThat(myReviewList.get(0).getId()).isEqualTo(1L);
            assertThat(myReviewList.get(0).getContent()).isEqualTo("testContent");
            assertThat(myReviewList.get(0).getTip()).isEqualTo("testTip");
            assertThat(myReviewList.get(0).getPlaceName()).isEqualTo("place1");
            assertThat(myReviewList.get(0).getScore()).isEqualTo(5);
            assertThat(myReviewList.get(1).getId()).isEqualTo(2L);
            assertThat(myReviewList.get(1).getPlaceName()).isEqualTo("place2");
        }

        @Test
        @DisplayName("실패 - 유저 정보가 없을시 예외발생")
        public void getMyReviewList_UserNotFoundException() {

            //given
            User user = createMockUser(1L, "testUser");
            when(userRepository.existsById(user.getId())).thenReturn(false);

            //when & then
            assertThatThrownBy(() -> reviewService.getMyReviewList(user.getId(), null))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessageContaining(USER_NOT_FOUND.getMessage());
        }
    }
}