package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.place.domain.ContentTypeResponseDto;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import com.triportreat.backend.place.service.ContentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentTypeServiceImpl implements ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    @Override
    public List<ContentTypeResponseDto> getContentTypes() {
        return contentTypeRepository.findAll().stream().map(ContentTypeResponseDto::toDto).toList();
    }

}
