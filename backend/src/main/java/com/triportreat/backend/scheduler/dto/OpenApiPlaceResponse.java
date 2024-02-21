package com.triportreat.backend.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenApiPlaceResponse {
    @JsonProperty("response")
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

        @JsonProperty("mapx")
        private Double latitude;

        @JsonProperty("mapy")
        private Double longitude;

        @JsonProperty("sigungucode")
        private Integer sigunguCode;
    }
}

