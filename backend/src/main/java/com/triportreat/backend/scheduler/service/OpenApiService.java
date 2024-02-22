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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OpenApiServiceImpl implements OpenApiService{

    private final OpenApiRepository openApiRepository;

    @Transactional
    public void updatePlace(List<Item> items) {
        openApiRepository.findAllRegion();
        openApiRepository.findAllContentType();
        openApiRepository.findAllSubCategory();
        openApiRepository.findAllPlace();

        items.stream()
                .filter(OpenApiServiceImpl::hasForeignKeyNull)
                .forEach(item -> {
                    Region region = openApiRepository.findRegionById(item.getRegionId());
                    ContentType contentType = openApiRepository.findContentTypeById(item.getContentTypeId());
                    SubCategory subCategory = openApiRepository.findSubCategoryById(item.getSubCategoryId());

                    Place place = setPlace(item, region, contentType, subCategory);
                    openApiRepository.update(place);
                });
    }

    private static Place setPlace(Item item, Region region, ContentType contentType, SubCategory subCategory) {
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

    private static boolean hasForeignKeyNull(Item item) {
        return Stream.of(item.getRegionId(), item.getContentTypeId(), item.getSubCategoryId())
                .anyMatch(Objects::isNull);
    }
}
