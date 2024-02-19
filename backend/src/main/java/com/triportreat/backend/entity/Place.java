package com.triportreat.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Place {

    @Id
    private Long id;

    //nullable?
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToOne
    @JoinColumn(name = "content_type_id")
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    private String address;

    private String addressDetail;

    @Column(length = 20, nullable = false)
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

    //기본값 설정은 어떻게??
    @Column(nullable = false)
    private Long views;

    @Column(length = 20)
    private String createdTime;

    @Column(length = 20)
    private String modifiedTime;

    public Place() {
    }

}
