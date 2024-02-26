package com.triportreat.backend.place.controller;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.place.service.ContentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;

@RestController
@RequiredArgsConstructor
public class ContentTypeController {

    private final ContentTypeService contentTypeService;

    @GetMapping("/places/content_type")
    public ResponseEntity<?> getContentTypes() {
        return ResponseEntity.ok().body(
                ResponseResult.success(GET_SUCCESS.getMessage(), contentTypeService.getContentTypes()));
    }
}
