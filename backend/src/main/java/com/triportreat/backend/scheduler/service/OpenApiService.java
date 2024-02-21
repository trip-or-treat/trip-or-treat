package com.triportreat.backend.scheduler.service;

import com.triportreat.backend.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final PlaceRepository placeRepository;

    public void updatePlaceData() {
        placeRepository.getTotalPages();
    }
}
