package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.PlaceRepositoryCustom;
import com.triportreat.backend.place.service.PlaceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepositoryCustom placeRepositoryCustom;
    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable) {
        return placeRepositoryCustom.searchPlaceListByCondition(placeSearchCondition, pageable);
    }
}
