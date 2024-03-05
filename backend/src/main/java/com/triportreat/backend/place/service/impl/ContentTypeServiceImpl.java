package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.place.domain.ContentTypeResponseDto;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import com.triportreat.backend.place.service.ContentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentTypeServiceImpl implements ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    @Override
    public List<ContentTypeResponseDto> getContentTypes() {
        return contentTypeRepository.findAll().stream()
                .filter(contentType -> !contentType.getName().equals("축제공연행사") &&
                        !contentType.getName().equals("여행코스"))
                .map(ContentTypeResponseDto::toDto)
                .collect(Collectors.toList());
    }

}
