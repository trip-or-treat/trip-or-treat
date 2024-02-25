package com.triportreat.backend.place.repository;

import com.triportreat.backend.place.dto.PlaceByRegionIdDto;
import com.triportreat.backend.place.dto.PlaceSearchCondition;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {

    List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable);
}
