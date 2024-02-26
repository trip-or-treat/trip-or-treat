package com.triportreat.backend.region.service.impl;

import com.triportreat.backend.region.domain.RegionDetailResponseDto;
import com.triportreat.backend.region.domain.RegionResponseDto;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.repository.RegionRepository;
import com.triportreat.backend.region.service.RegionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    @Override
    public List<RegionResponseDto> getRegions() {
        List<Region> regions = regionRepository.findAll();

        return regions.stream().map(RegionResponseDto::toDto).toList();
    }

    @Override
    public RegionDetailResponseDto getRegionDetail() {
        return null;
    }
}
