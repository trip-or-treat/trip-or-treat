package com.triportreat.backend.region.domain;

import com.triportreat.backend.region.entity.RecommendedPlace;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecommendedPlaceResponseDto {
    private Long id;
    private String name;
    private String overview;
    private String imageThumbnail;

    public static RecommendedPlaceResponseDto toDto(RecommendedPlace recommendedPlace) {
        return RecommendedPlaceResponseDto.builder()
                .id(recommendedPlace.getId())
                .name(recommendedPlace.getPlace().getName())
                .overview(recommendedPlace.getOverview())
                .imageThumbnail(recommendedPlace.getPlace().getImageThumbnail())
                .build();
    }
}
