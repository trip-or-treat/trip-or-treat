package com.triportreat.backend.scheduler.service;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.scheduler.dto.OpenApiPlaceResponseDto.Item;
import com.triportreat.backend.scheduler.repository.OpenApiRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService{

    private final OpenApiRepository openApiRepository;

    @Transactional
    public void updatePlace(List<Item> items) {
        persistAllRelatedData();

        updateAllPlaces(items);
    }

    private void updateAllPlaces(List<Item> items) {
        items.stream()
                .filter(OpenApiService::hasAllForeignKey)
                .forEach(item -> {
                    Region region = openApiRepository.findRegionById(item.getRegionId());
                    ContentType contentType = openApiRepository.findContentTypeById(item.getContentTypeId());
                    SubCategory subCategory = openApiRepository.findSubCategoryById(item.getSubCategoryId());

                    Place place = renewalPlace(item, region, contentType, subCategory);
                    openApiRepository.update(place);
                });
    }

    private void persistAllRelatedData() {
        openApiRepository.findAllRegion();
        openApiRepository.findAllContentType();
        openApiRepository.findAllSubCategory();
        openApiRepository.findAllPlace();
    }

    private static boolean hasAllForeignKey(Item item) {
        return Stream.of(item.getRegionId(), item.getContentTypeId(), item.getSubCategoryId())
                .noneMatch(Objects::isNull);
    }

    private Place renewalPlace(Item item, Region region, ContentType contentType, SubCategory subCategory) {
        return Place.builder()
                .id(item.getId())
                .region(region)
                .contentType(contentType)
                .subCategory(subCategory)
                .address(item.getAddress())
                .addressDetail(item.getAddressDetail())
                .name(item.getName())
                .mainCategoryId(item.getMainCategoryId())
                .midCategoryId(item.getMidCategoryId())
                .imageOrigin(item.getImageOrigin())
                .imageThumbnail(item.getImageThumbnail())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .sigunguCode(item.getSigunguCode())
                .build();
    }
}
