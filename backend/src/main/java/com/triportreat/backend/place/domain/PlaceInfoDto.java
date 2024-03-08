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
    private String imageThumbnail;
    private String address;
    private String overview;
    private Long contentTypeId;

    public static PlaceInfoDto toDto(Place place, ContentType contentType, String overview) {
        return PlaceInfoDto.builder()
                .name(place.getName())
                .imageThumbnail(place.getImageOrigin())
                .address(place.getAddress())
                .overview(overview)
                .contentTypeId(contentType.getId())
                .build();
    }
}
