package com.triportreat.backend.place.service;

import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceCommonInfoDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PlaceService {
    List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable);

    PlaceCommonInfoDto getPlaceCommonInfo(Long id);

    void updatePlacesByExternalApi();
}
