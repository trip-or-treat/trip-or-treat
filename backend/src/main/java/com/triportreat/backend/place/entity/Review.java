package com.triportreat.backend.place.entity;

import com.triportreat.backend.common.BaseTimeEntity;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(length = 65535)
    private String tip;

    @Column(length = 65535, nullable = false)
    private String content;

    @Column(nullable = false)
    private Float score;

    public static Review toEntity(ReviewRequestDto reviewRequestDto, User user, Place place) {
        return Review.builder()
                .user(user)
                .place(place)
                .content(reviewRequestDto.getContent())
                .tip(reviewRequestDto.getTip())
                .score(reviewRequestDto.getScore())
                .build();
    }

    public void reviewUpdate(String content, String tip, Float score) {
        this.content = content;
        this.tip = tip;
        this.score = score;
    }
}
