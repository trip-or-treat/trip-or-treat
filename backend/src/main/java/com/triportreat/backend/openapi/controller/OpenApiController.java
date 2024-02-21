package com.triportreat.backend.openapi.controller;

import com.triportreat.backend.openapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping("/place")
    public void storeInitPlaceData() {
        openApiService.storeInitPlaceData();
    }

    @GetMapping("/set-place")
    public void setPlaceTest() {
        openApiService.setPlaceTest();
    }

}
