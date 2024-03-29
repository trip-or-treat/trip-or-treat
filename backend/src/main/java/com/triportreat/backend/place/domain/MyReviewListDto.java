package com.triportreat.backend.place.domain;

import com.triportreat.backend.common.utils.CustomDateUtil;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewListDto {

    private  Long id;
    private  String content;
    private  String placeName;
    private  Integer score;
    private String createdDate;

    public static MyReviewListDto toDto(Review review, Place place) {
        String formattedDate = review.getCreatedDate() != null ? CustomDateUtil.toStringFormat(review.getCreatedDate()) : "날짜 정보 없음";

        return MyReviewListDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .placeName(place.getName())
                .score(review.getScore())
                .createdDate(formattedDate)
                .build();
    }
}