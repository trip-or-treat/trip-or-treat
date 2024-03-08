package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.ReviewRepository;
import com.triportreat.backend.place.service.ReviewListService;
import com.triportreat.backend.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;



@SpringBootTest
public class ReviewListServiceImplTest {

    @Autowired
    private ReviewListService reviewListService;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private PlaceRepository placeRepository;

    @BeforeEach
    public void setup() {
        User user = User.builder().id(1L).nickname("testUser").imageThumbnail("testImage").build();
        Review review1 = Review.builder().id(1L).content("testContent1").tip("testTip1").score(5.0f).user(user).build();
        Review review2 = Review.builder().id(2L).content("testContent2").tip("testTip2").score(1.0f).user(user).build();
        List<Review> reviews = Arrays.asList(review1, review2);
        given(reviewRepository.findByPlaceId(1L, Pageable.unpaged())).willReturn(reviews);
        given(placeRepository.existsById(1L)).willReturn(true);
    }

    @Test
    @DisplayName("장소 ID에 해당하는 리뷰 목록을 조회한다")
    public void getReviewListTest() {
        List<ReviewListDto> reviewList = reviewListService.getReviewList(1L, Pageable.unpaged());
        assertThat(reviewList).hasSize(2);
        assertThat(reviewList.get(0).getId()).isEqualTo(1L);
        assertThat(reviewList.get(0).getNickname()).isEqualTo("testUser");
        assertThat(reviewList.get(0).getImageThumbnail()).isEqualTo("testImage");
        assertThat(reviewList.get(0).getContent()).isEqualTo("testContent1");
        assertThat(reviewList.get(0).getTip()).isEqualTo("testTip1");
        assertThat(reviewList.get(0).getScore()).isEqualTo(5.0f);
        assertThat(reviewList.get(1).getContent()).isEqualTo("testContent2");
    }
}