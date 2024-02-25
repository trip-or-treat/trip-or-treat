package com.triportreat.backend.place.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PlaceByRegionIdDto {

    private Long id;
    private String name;
    private String imageThumbnail;
    private String subCategoryName;
    private Double latitude;
    private Double longitude;
    private Long contentTypeId;

    @QueryProjection
    public PlaceByRegionIdDto(Long id, String name, String imageThumbnail, String subCategoryName, Double latitude,
                              Double longitude, Long contentTypeId) {
        this.id = id;
        this.name = name;
        this.imageThumbnail = imageThumbnail;
        this.subCategoryName = subCategoryName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contentTypeId = contentTypeId;
    }
}
