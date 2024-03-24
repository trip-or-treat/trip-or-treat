package com.triportreat.backend.place.domain;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewListDto {

    private  Long id;
    private  String content;
    private  String tip;
    private  String placeName;
    private  Integer score;
    private  LocalDateTime createdDate;

    public static MyReviewListDto toDto(Review review, Place place) {
        return MyReviewListDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .tip(review.getTip())
                .placeName(place.getName())
                .score(review.getScore())
                .createdDate(review.getCreatedDate())
                .build();
    }
}