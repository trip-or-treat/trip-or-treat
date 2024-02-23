package com.triportreat.backend.place.entity;

import com.triportreat.backend.common.BaseTimeEntity;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.scheduler.dto.OpenApiPlaceResponseDto.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Place extends BaseTimeEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_type_id")
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    private String address;

    private String addressDetail;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 10)
    private String mainCategoryId;

    @Column(length = 10)
    private String midCategoryId;

    private String imageOrigin;

    private String imageThumbnail;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private Integer sigunguCode;

    @Column(nullable = false)
    private Long views;

    public static Place createPlace(Item item, Region region, ContentType contentType, SubCategory subCategory) {
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
                .views(0L)
                .build();
    }

    public void update(Item item) {
        this.name = item.getName();
        this.address = item.getAddress();
        this.addressDetail = item.getAddressDetail();
        this.mainCategoryId = item.getMainCategoryId();
        this.midCategoryId = item.getMidCategoryId();
        this.imageOrigin = item.getImageOrigin();
        this.imageThumbnail = item.getImageThumbnail();
        this.latitude = item.getLatitude();
        this.longitude = item.getLongitude();
        this.sigunguCode = item.getSigunguCode();
    }
}
