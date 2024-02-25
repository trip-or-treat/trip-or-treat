package com.triportreat.backend.place.service;

import com.triportreat.backend.place.dto.PlaceByRegionIdDto;
import com.triportreat.backend.place.dto.PlaceSearchCondition;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PlaceService {
    List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable);
}
