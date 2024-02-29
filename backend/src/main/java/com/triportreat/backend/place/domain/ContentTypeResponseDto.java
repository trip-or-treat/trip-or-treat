package com.triportreat.backend.place.domain;

import com.triportreat.backend.place.entity.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ContentTypeResponseDto {
        private Long id;
        private String name;

        public static ContentTypeResponseDto toDto(ContentType contentType) {
            return ContentTypeResponseDto.builder()
                    .id(contentType.getId())
                    .name(contentType.getName())
                    .build();
        }
}
