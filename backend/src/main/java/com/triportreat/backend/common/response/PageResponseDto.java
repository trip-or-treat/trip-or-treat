package com.triportreat.backend.common.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageResponseDto<T> {
    @Builder.Default
    List<T> contents = new ArrayList<>();

    private Integer page;
    private Integer totalPages;
    private Boolean prev;
    private Boolean next;
    private Integer startPage;
    private Integer endPage;

    public PageResponseDto(Page<T> page) {
        this.contents = page.getContent();
        this.page = page.getNumber() + 1;
        this.totalPages = page.getTotalPages();
        this.prev = page.hasPrevious();
        this.next = page.hasNext();
        calculateStartPageAndEndPageNumber();
    }

    private void calculateStartPageAndEndPageNumber() {
        int tempEnd = (int) (Math.ceil(page/5.0)) * 5;
        this.startPage = tempEnd - 4;
        this.endPage = totalPages > tempEnd ? tempEnd : totalPages;
    }
}