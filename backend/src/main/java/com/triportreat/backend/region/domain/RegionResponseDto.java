package com.triportreat.backend.region.domain;

import com.triportreat.backend.region.entity.Region;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegionResponseDto {
    private Long id;
    private String name;
    private String imageOrigin;
    private String imageThumbnail;
    private Double latitude;
    private Double longitude;

    public static RegionResponseDto toDto(Region region) {
        return RegionResponseDto.builder()
                .id(region.getId())
                .name(region.getName())
                .imageOrigin(region.getImageOrigin())
                .imageThumbnail(region.getImageThumbnail())
                .latitude(region.getLatitude())
                .longitude(region.getLongitude())
                .build();
    }
}
