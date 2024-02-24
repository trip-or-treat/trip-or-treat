package com.triportreat.backend.place.controller;

import com.triportreat.backend.place.dto.PlaceRequestDto;
import com.triportreat.backend.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public String getPlacesByRegionId(@ModelAttribute PlaceRequestDto placeRequestDto) {
        return placeService.getPlacesByRegionId(placeRequestDto);
    }
}
