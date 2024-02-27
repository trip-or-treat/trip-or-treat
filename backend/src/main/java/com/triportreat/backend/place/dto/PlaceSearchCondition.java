package com.triportreat.backend.place.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlaceSearchCondition {

    @NotNull(message = "지역 ID는 필수입니다.")
    private Long regionId;

    private String keyword;

    private Long contentTypeId;

}
