package com.triportreat.backend.region.domain;

import com.triportreat.backend.region.entity.RecommendedPlace;
import com.triportreat.backend.region.entity.Region;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegionDetailResponseDto {
    private Long id;
    private String name;
    private String imageThumbnail;
    private String overview;
    private Double latitude;
    private Double longitude;
    private List<RecommendedPlaceResponseDto> recommendedPlaces = new ArrayList<>();

    public static RegionDetailResponseDto toDto(Region region, List<RecommendedPlace> recommendedPlaces) {
        return RegionDetailResponseDto.builder()
                .id(region.getId())
                .name(region.getName())
                .imageThumbnail(region.getImageThumbnail())
                .overview(region.getOverview())
                .latitude(region.getLatitude())
                .longitude(region.getLongitude())
                .recommendedPlaces(recommendedPlaces.stream()
                        .map(RecommendedPlaceResponseDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
