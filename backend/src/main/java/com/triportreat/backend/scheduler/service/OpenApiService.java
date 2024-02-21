package com.triportreat.backend.scheduler.service;

import com.triportreat.backend.scheduler.repository.OpenApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiRepository openApiRepository;

    public void updatePlaceData() {
        openApiRepository.updatePlaceData();
    }
}
