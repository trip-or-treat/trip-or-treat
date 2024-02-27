package com.triportreat.backend.place.repository;

import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {

    List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable);
}
