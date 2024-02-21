package com.triportreat.backend.place.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SubCategory {

    @Id
    @Column(length = 10)
    private String id;

    @Column(length = 10, nullable = false)
    private String name;

}
