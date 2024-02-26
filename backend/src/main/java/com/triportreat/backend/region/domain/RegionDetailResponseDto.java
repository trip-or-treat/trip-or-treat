package com.triportreat.backend.region.domain;

import java.util.List;
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
    private String imageOrigin;
    private String overview;
    private Double latitude;
    private Double longitude;
    private List<RecommendedPlaceResponseDto> recommendedPlaces;
}
