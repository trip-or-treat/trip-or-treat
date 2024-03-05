package com.triportreat.backend.place.domain;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceCommonInfoDto {
    private String name;
    private String imageOrigin;
    private String overview;
    private Long contentTypeId;

    public static  PlaceCommonInfoDto toDto(Place place, ContentType contentType, String overview) {
        return PlaceCommonInfoDto.builder()
                .name(place.getName())
                .imageOrigin(place.getImageOrigin())
                .overview(overview)
                .contentTypeId(contentType.getId())
                .build();
    }
}
