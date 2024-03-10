package com.triportreat.backend.place.service.impl;


import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.ReviewRepository;
import com.triportreat.backend.place.service.ReviewListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewListServiceImpl implements ReviewListService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewListDto> getReviewList(Long id, Pageable pageable) {
        List<Review> reviews = reviewRepository.findByPlaceId(id, pageable);

        if (!placeRepository.existsById(id)) {
            throw new PlaceNotFoundException(id);
        }

        return reviews.stream().map((review -> ReviewListDto.toDto(review, review.getUser()))).collect(Collectors.toList());
    }
}

