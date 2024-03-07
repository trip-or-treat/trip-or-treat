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
public class OpenApiService{

    private final OpenApiRepository openApiRepository;

    @Transactional
    public void upsertPlaces(List<Item> items) {
        persistAllRelatedData();
        upsertPlace(items);
    }

    private void upsertPlace(List<Item> items) {
        items.stream()
                .filter(OpenApiService::hasAllForeignKey)
                .forEach(item -> {
                    Place place = openApiRepository.findPlaceById(item.getId());
                    if (place == null) {
                        Region region = openApiRepository.findRegionById(item.getRegionId());
                        ContentType contentType = openApiRepository.findContentTypeById(item.getContentTypeId());
                        SubCategory subCategory = openApiRepository.findSubCategoryById(item.getSubCategoryId());

                        Place newPlace = Place.createPlace(item, region, contentType, subCategory);
                        openApiRepository.savePlace(newPlace);
                    }
                    else {
                        place.updatePlaceData(item);
                    }
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
}
