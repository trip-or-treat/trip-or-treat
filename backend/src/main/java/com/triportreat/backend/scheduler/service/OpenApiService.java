package com.triportreat.backend.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenApiService {

//    private final PlaceRepository placeRepository;

    @Value("${openapi.url}")
    private String baseUrl;

    @Value("${openapi.key}")
    private String serviceKey;

    @Value("${openapi.rows}")
    private Integer numOfRows;

    public void updatePlaceData() {
    }
}
