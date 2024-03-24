package com.triportreat.backend.place.service.impl;


import com.triportreat.backend.place.domain.MyReviewListDto;
import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.error.handler.exception.UserNotFoundException;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.ReviewRepository;
import com.triportreat.backend.place.service.ReviewService;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewListDto> getReviewList(Long id, Pageable pageable) {
        List<Review> reviews = reviewRepository.findByPlaceId(id, pageable);

        if (!placeRepository.existsById(id)) {
            throw new PlaceNotFoundException(id);
        }

        return reviews.stream().map((review -> ReviewListDto.toDto(review, review.getUser()))).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createReview(ReviewRequestDto reviewRequestDto) {
        Place place = placeRepository.findById(reviewRequestDto.getPlaceId())
                .orElseThrow(() -> new PlaceNotFoundException(reviewRequestDto.getPlaceId()));

        User user = userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Review review = Review.toEntity(reviewRequestDto, user, place);
        reviewRepository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyReviewListDto> getMyReviewList(Long id, Pageable pageable) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        List<Review> reviews = reviewRepository.findByUserId(id, pageable);

        return reviews.stream().map(review -> {
            Place place = review.getPlace();
            return MyReviewListDto.toDto(review, place);
        }).collect(Collectors.toList());
    }
}

