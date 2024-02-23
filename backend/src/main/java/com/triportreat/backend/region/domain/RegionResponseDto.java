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
    private String image_origin;
    private String image_thumbnail;
    private Double mapX;
    private Double mapY;

    public static RegionResponseDto toDto(Region region) {
        return RegionResponseDto.builder()
                .id(region.getId())
                .name(region.getName())
                .image_origin(region.getImageOrigin())
                .image_thumbnail(region.getImageThumbnail())
                .mapX(region.getLatitude())
                .mapY(region.getLongitude())
                .build();
    }
}
