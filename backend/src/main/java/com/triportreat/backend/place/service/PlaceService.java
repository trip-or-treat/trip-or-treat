package com.triportreat.backend.place.service;

import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceInfoDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceService {
    List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable);

    PlaceInfoDto getPlaceInfo(Long id);
}
