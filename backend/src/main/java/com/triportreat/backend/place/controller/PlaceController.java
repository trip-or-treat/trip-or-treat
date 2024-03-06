package com.triportreat.backend.place.controller;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<?> searchPlaceListByCondition(
            @Valid PlaceSearchCondition placeSearchCondition,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(
                        ResponseResult.success(
                                GET_SUCCESS.getMessage(),
                                placeService.searchPlaceListByCondition(placeSearchCondition, pageable)));
    }

    @GetMapping("{id}/info")
    public ResponseEntity<?> getPlaceCommonInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(
                ResponseResult.success(GET_SUCCESS.getMessage(), placeService.getPlaceCommonInfo(id))
        );
    }
}
