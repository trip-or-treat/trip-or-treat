package com.triportreat.backend.region.service.impl;

import com.triportreat.backend.region.domain.RegionDetailResponseDto;
import com.triportreat.backend.region.domain.RegionResponseDto;
import com.triportreat.backend.region.entity.RecommendedPlace;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.error.exception.RegionNotFoundException;
import com.triportreat.backend.region.repository.RecommendedPlaceRepository;
import com.triportreat.backend.region.repository.RegionRepository;
import com.triportreat.backend.region.service.RegionService;
import com.triportreat.backend.region.service.RegionValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RecommendedPlaceRepository recommendedPlaceRepository;
    private final RegionValidator regionValidator;

    @Transactional(readOnly = true)
    @Override
    public List<RegionResponseDto> getRegions() {
        List<Region> regions = regionRepository.findAll();

        return regions.stream().map(RegionResponseDto::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public RegionDetailResponseDto getRegionDetail(Long id) {
        Region region = regionRepository.findById(id).orElseThrow(RegionNotFoundException::new);

        List<RecommendedPlace> recommendedPlaces = recommendedPlaceRepository.findByRegion(region);
        regionValidator.validateRecommendedPlacesEmpty(recommendedPlaces);

        return RegionDetailResponseDto.toDto(region, recommendedPlaces);
    }
}
