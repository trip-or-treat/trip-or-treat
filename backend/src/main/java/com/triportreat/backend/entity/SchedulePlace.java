package com.practice.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SchedulePlace {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(nullable = false)
    private Integer visitOrder;

    //null이어도 합계 계산할 때 문제없는가?
    private Integer expense;

    @Column(length = 65535)
    private String memo;

    public SchedulePlace() {
    }
}
