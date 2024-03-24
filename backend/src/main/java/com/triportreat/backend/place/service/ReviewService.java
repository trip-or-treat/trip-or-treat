package com.triportreat.backend.place.service;

import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.domain.ReviewUpdateRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    List<ReviewListDto> getReviewList(Long id, Pageable pageable);
    void createReview(ReviewRequestDto reviewRequestDto);
    void updateReview(Long id,ReviewUpdateRequestDto reviewUpdateRequestDto);
}