package com.triportreat.backend.region.service;

import com.triportreat.backend.region.domain.RegionDetailResponseDto;
import com.triportreat.backend.region.domain.RegionResponseDto;
import java.util.List;

public interface RegionService {
    List<RegionResponseDto> getRegions();

    RegionDetailResponseDto getRegionDetail(Long id);
}
