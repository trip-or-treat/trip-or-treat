package com.triportreat.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Region {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private String imageOrigin;

    private String imageThumbnail;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 65535)
    private String overview;

    public Region() {
    }
}
