package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceCommonInfoDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.PlaceRepositoryCustom;
import com.triportreat.backend.place.service.ExternalApiService;
import com.triportreat.backend.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepositoryCustom placeRepositoryCustom;
    private final PlaceRepository placeRepository;
    private final ExternalApiService externalApiService;

    @Override
    @Transactional(readOnly = true)
    public List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable) {
        return placeRepositoryCustom.searchPlaceListByCondition(placeSearchCondition, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceCommonInfoDto getPlaceCommonInfo(Long id) {
        Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceNotFoundException(id));
        ContentType contentType = place.getContentType();
        String overview = externalApiService.callExternalApiForOverView(id);
        return PlaceCommonInfoDto.toDto(place, contentType, overview);
    }
}

