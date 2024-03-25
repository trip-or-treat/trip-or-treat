package com.triportreat.backend.place.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceByRegionIdDto {

    private Long placeId;
    private String name;
    private String imageThumbnail;
    private String subCategoryName;
    private Double latitude;
    private Double longitude;
    private Long contentTypeId;
    private Long views;

    @QueryProjection
    public PlaceByRegionIdDto(Long placeId, String name, String imageThumbnail, String subCategoryName, Double latitude,
                              Double longitude, Long contentTypeId, Long views) {
        this.placeId = placeId;
        this.name = name;
        this.imageThumbnail = imageThumbnail;
        this.subCategoryName = subCategoryName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contentTypeId = contentTypeId;
        this.views = views;
    }
}
