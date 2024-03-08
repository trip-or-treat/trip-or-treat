package com.triportreat.backend.place.service;

import com.triportreat.backend.place.domain.ReviewListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewListService {
    List<ReviewListDto> getReviewList(Long id, Pageable pageable);
}