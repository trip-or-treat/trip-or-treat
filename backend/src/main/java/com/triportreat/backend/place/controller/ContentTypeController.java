package com.triportreat.backend.place.controller;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.service.ContentTypeService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContentTypeController {

    private final ContentTypeService contentTypeService;


    @GetMapping("/places/content_type")
    public Result getContentTypes() {
        List<ContentTypeDto> collect = contentTypeService.findContentTypes().stream()
                .map(c -> new ContentTypeDto(c.getId(), c.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    @Builder
    static class Result<T>{
        private T body;
    }

    @Data
    @AllArgsConstructor
    static class ContentTypeDto {
        private Long id;
        private String name;
    }
}
