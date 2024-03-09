package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.common.cache.RedisService;
import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceCommonInfoDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.PlaceRepositoryCustom;
import com.triportreat.backend.place.repository.SubCategoryRepository;
import com.triportreat.backend.place.service.ExternalApiService;
import com.triportreat.backend.place.service.PlaceService;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.repository.RegionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepositoryCustom placeRepositoryCustom;
    private final PlaceRepository placeRepository;
    private final RegionRepository regionRepository;
    private final ContentTypeRepository contentTypeRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ExternalApiService externalApiService;
    private final RedisService redisService;

    @Override
    @Transactional(readOnly = true)
    public List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition,
                                                               Pageable pageable) {
        return placeRepositoryCustom.searchPlaceListByCondition(placeSearchCondition, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceCommonInfoDto getPlaceCommonInfo(Long id) {
        Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceNotFoundException(id));
        ContentType contentType = place.getContentType();
        String overview = externalApiService.callExternalApiForOverView(id);

        redisService.increasePlaceView(id, place.getViews());
        return PlaceCommonInfoDto.toDto(place, contentType, overview);
    }

    @Override
    @Transactional
    public void updatePlacesByExternalApi() {
        List<Item> items = externalApiService.callExternalApiForUpdatePlaces();
        placeRepository.findAll();

        List<Place> newPlaces = new ArrayList<>();
        items.stream()
                .filter(PlaceServiceImpl::isForeignKeyPresent)
                .filter(PlaceServiceImpl::isContentTypeIdNotEqualsToFestivalOrCourse)
                .forEach(item -> {
                    placeRepository.findById(item.getId()).ifPresentOrElse(
                            p -> p.updateByTourApi(item),
                            () -> {
                                Optional<Region> region = regionRepository.findById(item.getRegionId());
                                Optional<ContentType> contentType = contentTypeRepository.findById(item.getContentTypeId());
                                Optional<SubCategory> subCategory = subCategoryRepository.findById(item.getSubCategoryId());
                                if (isForeignKeyAvailable(region, contentType, subCategory)){
                                    newPlaces.add(Item.toEntity(item, region.get(), contentType.get(), subCategory.get()));
                                }
                            }
                    );
                });
        placeRepository.saveAll(newPlaces);
    }

    private static boolean isForeignKeyPresent(Item item) {
        return Stream.of(item.getRegionId(), item.getContentTypeId(), item.getSubCategoryId())
                .noneMatch(Objects::isNull);
    }

    private static boolean isContentTypeIdNotEqualsToFestivalOrCourse(Item item) {
        return item.getContentTypeId() != 15 && item.getContentTypeId() != 25;
    }

    private static boolean isForeignKeyAvailable(Optional<Region> region,
                                                 Optional<ContentType> contentType,
                                                 Optional<SubCategory> subCategory) {
        return region.isPresent() && contentType.isPresent() && subCategory.isPresent();
    }
}

