package com.triportreat.backend.place.repository;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("장소 ID에 따른 리뷰 조회")
    void findByPlaceIdTest() {
        //given
        User user = User.builder()
                .nickname(("testname"))
                .imageThumbnail("image")
                .build();
        userRepository.save(user);

        Place place = Place.builder()
                .id(1L)
                .name("test name")
                .latitude(123.456)
                .longitude(12.3456)
                .views(10L)
                .build();
        placeRepository.save(place);

        Review review1 = Review.builder()
                .id(1L)
                .content("review1")
                .tip("tip1")
                .score(5.0f)
                .place(place)
                .build();
        reviewRepository.save(review1);

        Review review2 = Review.builder()
                .id(2L)
                .content("review2")
                .tip("tip2")
                .score(3.0f)
                .place(place)
                .build();
        reviewRepository.save(review2);

        //when
        Pageable pageable = PageRequest.of(0, 10);
        List<Review> reviews = reviewRepository.findByPlaceId(place.getId(), pageable);

        //then
        assertThat(reviews.get(0).getContent()).isEqualTo("review1");
        assertThat(reviews).hasSize(2);
    }
}