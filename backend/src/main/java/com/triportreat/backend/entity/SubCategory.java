package com.triportreat.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SubCategory {

    @Id
    @Column(length = 10)
    private String id;

    @Column(length = 10, nullable = false)
    private String name;

    public SubCategory() {
    }
}
