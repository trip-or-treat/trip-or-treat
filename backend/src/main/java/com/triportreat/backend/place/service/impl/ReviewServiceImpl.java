package com.triportreat.backend.place.service.impl;


import com.triportreat.backend.common.response.PageResponseDto;
import com.triportreat.backend.place.domain.MyReviewListDto;
import com.triportreat.backend.common.error.exception.AuthenticateFailException;
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
import com.triportreat.backend.place.service.ReviewService;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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
    public void createReview(ReviewRequestDto reviewRequestDto) {
        Place place = placeRepository.findById(reviewRequestDto.getPlaceId())
                .orElseThrow(() -> new PlaceNotFoundException(reviewRequestDto.getPlaceId()));

        User user = userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Review review = Review.toEntity(reviewRequestDto, user, place);
        reviewRepository.save(review);
    }

    @Override
    public void updateReview(Long id, ReviewUpdateRequestDto reviewUpdateRequestDto) {
        Place place = placeRepository.findById(reviewUpdateRequestDto.getPlaceId())
                .orElseThrow(() -> new PlaceNotFoundException(reviewUpdateRequestDto.getPlaceId()));

        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFoundException::new);

        if(!review.getPlace().equals(place)) {
            throw new ReviewNotBelongPlaceException();
        }

        review.reviewUpdate(reviewUpdateRequestDto.getContent(), reviewUpdateRequestDto.getTip(), reviewUpdateRequestDto.getScore());
    }

    @Override
    public void deleteReview(Long userId, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.getUser().getId().equals(userId)) {
            throw new AuthenticateFailException();
        }

        reviewRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<MyReviewListDto> getMyReviewList(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        Page<Review> reviewPage = reviewRepository.findByUserId(userId, pageable);

        return new PageResponseDto<>(reviewPage.map(review -> MyReviewListDto.toDto(review, review.getPlace())));
    }
}

