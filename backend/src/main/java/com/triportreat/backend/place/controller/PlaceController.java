package com.triportreat.backend.place.controller;

import com.triportreat.backend.common.response.Success;
import com.triportreat.backend.place.dto.PlaceSearchCondition;
import com.triportreat.backend.place.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<?> searchPlaceListByCondition(
            @Valid PlaceSearchCondition placeSearchCondition,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(
                Success.response("조회에 성공하였습니다!",placeService.searchPlaceListByCondition(placeSearchCondition, pageable)));
    }
}
