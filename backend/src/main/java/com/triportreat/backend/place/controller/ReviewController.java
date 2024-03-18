package com.triportreat.backend.place.controller;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("places/{id}/review")
    public ResponseEntity<?> getReviewList(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(ResponseResult.success(GET_SUCCESS.getMessage(), reviewService.getReviewList(id, pageable)));
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> postReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewRequestDto.setUserId(1L); // TODO 로그인 기능 구현 완료시 수정 필요
        reviewService.createReview(reviewRequestDto);
        return ResponseEntity.ok().body(ResponseResult.success(POST_SUCCESS.getMessage(), null));
    }
}