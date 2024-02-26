package com.triportreat.backend.region.domain;

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
}
