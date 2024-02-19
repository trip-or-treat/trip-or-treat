package com.practice.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ContentType {

    @Id
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;
}
