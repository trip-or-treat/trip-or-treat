package com.triportreat.backend.place.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlaceSearchCondition {

    @NotNull
    private Long regionId;

    private String keyword;

    private Long contentTypeId;

}
