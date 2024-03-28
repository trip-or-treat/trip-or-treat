package com.triportreat.backend.place.domain;

import com.triportreat.backend.place.entity.Review;
import com.triportreat.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListDto {

    private Long id;
    private String nickname;
    private String imageThumbnail;
    private String content;
    private String tip;
    private Integer score;
    private LocalDateTime createdDate;

    public static ReviewListDto toDto(Review review, User user) {
        return ReviewListDto.builder()
                .id(review.getId())
                .nickname(user.getNickname())
                .imageThumbnail(user.getImageThumbnail())
                .content(review.getContent())
                .tip(review.getTip())
                .score(review.getScore())
                .createdDate(review.getCreatedDate())
                .build();
    }
}