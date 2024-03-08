package com.triportreat.backend.place.controller;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.place.service.ReviewListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewListService reviewListService;

    @GetMapping("places/{id}/review")
    public ResponseEntity<?> getReviewList(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.ok().body(ResponseResult.success(GET_SUCCESS.getMessage(), reviewListService.getReviewList(id, pageable)));
    }
}