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
    private Long totalElements;
    private Boolean prev;
    private Boolean next;
    private Boolean first;
    private Boolean last;
    private Integer startPage;
    private Integer endPage;

    public PageResponseDto(Page<T> page) {
        this.contents = page.getContent();
        this.page = page.getNumber() + 1;
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.prev = page.hasPrevious();
        this.next = page.hasNext();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.startPage = calculateStartPage();
        this.endPage = calculateEndPage(page);
    }

    private int calculateStartPage() {
        int blockSize = 5;
        int blockNumber = (int) Math.ceil((double) this.page / blockSize);
        return (blockNumber - 1) * blockSize + 1;
    }

    private int calculateEndPage(Page<T> page) {
        int lastItemIndex = this.page * page.getPageable().getPageSize() - 1;
        int lastPageNumber = (int) Math.ceil((double) page.getTotalElements() / page.getPageable().getPageSize());
        return Math.min(lastItemIndex, lastPageNumber);
    }
}