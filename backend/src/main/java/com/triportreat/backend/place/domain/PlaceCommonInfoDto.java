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
    private String imageThumbnail;
    private String overview;
    private Long contentTypeId;
    private String address;

    public static  PlaceCommonInfoDto toDto(Place place, ContentType contentType, String overview) {
        return PlaceCommonInfoDto.builder()
                .name(place.getName())
                .imageThumbnail(place.getImageOrigin())
                .overview(overview)
                .contentTypeId(contentType.getId())
                .address(place.getAddress())
                .build();
    }
}
