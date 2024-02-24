package com.triportreat.backend.place.service;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    public List<ContentType> findContentTypes() {
        return contentTypeRepository.findAll();
    }
}
