package com.triportreat.backend.place.service;

import com.triportreat.backend.place.domain.ContentTypeResponseDto;

import java.util.List;

public interface ContentTypeService {
    public List<ContentTypeResponseDto> getContentTypes();
}
