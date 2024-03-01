package com.triportreat.backend.region.service;

import com.triportreat.backend.region.entity.RecommendedPlace;
import com.triportreat.backend.region.error.exception.RecommendedPlacesNotFoundException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RegionValidator {

    public void validateRecommendedPlacesEmpty(List<RecommendedPlace> recommendedPlaces) {
        if (recommendedPlaces.isEmpty()) {
            throw new RecommendedPlacesNotFoundException();
        }
    }
}
