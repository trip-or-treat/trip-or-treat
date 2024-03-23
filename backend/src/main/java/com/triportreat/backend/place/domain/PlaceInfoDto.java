package com.triportreat.backend.place.domain;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceInfoDto {
    private String name;
    private String imageOrigin;
    private String address;
    private String overview;
    private Long contentTypeId;
    private String contentTypeName;

    public static PlaceInfoDto toDto(Place place, ContentType contentType, String overview) {
        return PlaceInfoDto.builder()
                .name(place.getName())
                .imageOrigin(place.getImageOrigin())
                .address(place.getAddress())
                .overview(overview)
                .contentTypeId(contentType.getId())
                .contentTypeName(contentType.getName())
                .build();
    }
}
