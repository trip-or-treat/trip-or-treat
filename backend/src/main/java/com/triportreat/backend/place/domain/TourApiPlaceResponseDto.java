package com.triportreat.backend.place.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.region.entity.Region;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourApiPlaceResponseDto {
    @JsonProperty("response")
    @NotEmpty
    private ResponseData response;

    @Getter @Setter
    public static class ResponseData {
        private Header header;
        private Body body;
    }

    @Getter @Setter
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Getter @Setter
    public static class Body {
        private Items items;

        @JsonProperty("numOfRows")
        private int numOfRows;

        @JsonProperty("pageNo")
        private int pageNo;

        @JsonProperty("totalCount")
        private int totalCount;
    }

    @Getter @Setter
    public static class Items {
        private List<Item> item;
    }

    @Getter @Setter
    @Builder
    public static class Item {
        @JsonProperty("contentid")
        private Long id;

        @JsonProperty("areacode")
        private Long regionId;

        @JsonProperty("contenttypeid")
        private Long contentTypeId;

        @JsonProperty("cat3")
        private String subCategoryId;

        @JsonProperty("addr1")
        private String address;

        @JsonProperty("addr2")
        private String addressDetail;

        @JsonProperty("title")
        private String name;

        @JsonProperty("cat1")
        private String mainCategoryId;

        @JsonProperty("cat2")
        private String midCategoryId;

        @JsonProperty("firstimage")
        private String imageOrigin;

        @JsonProperty("firstimage2")
        private String imageThumbnail;

        @JsonProperty("mapy")
        private Double latitude;

        @JsonProperty("mapx")
        private Double longitude;

        @JsonProperty("sigungucode")
        private Integer sigunguCode;

        public static Place toEntity(Item item, Region region, ContentType contentType, SubCategory subCategory) {
            return Place.builder()
                    .id(item.getId())
                    .region(region)
                    .contentType(contentType)
                    .subCategory(subCategory)
                    .address(item.getAddress())
                    .addressDetail(item.getAddressDetail())
                    .name(item.getName())
                    .mainCategoryId(item.getMainCategoryId())
                    .midCategoryId(item.getMidCategoryId())
                    .imageOrigin(item.getImageOrigin())
                    .imageThumbnail(item.getImageThumbnail())
                    .latitude(item.getLatitude())
                    .longitude(item.getLongitude())
                    .sigunguCode(item.getSigunguCode())
                    .build();
        }
    }
}

