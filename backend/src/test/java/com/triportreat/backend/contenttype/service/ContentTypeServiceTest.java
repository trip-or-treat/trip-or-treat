package com.triportreat.backend.contenttype.service;

import com.triportreat.backend.place.domain.ContentTypeResponseDto;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import com.triportreat.backend.place.service.impl.ContentTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ContentTypeServiceTest {

    @InjectMocks
    private ContentTypeServiceImpl contentTypeService;

    @Mock
    private ContentTypeRepository contentTypeRepository;

    @Test
    public void testGetContentTypes() {
        List<ContentType> contentTypes = new ArrayList<>();
        ContentType contentType1 = ContentType.builder()
                .id(1L)
                .name("test1")
                .build();
        ContentType contentType2 = ContentType.builder()
                .id(2L)
                .name("test2")
                .build();
        ContentType contentType3 = ContentType.builder()
                .id(3L)
                .name("test3")
                .build();
        contentTypes.add(contentType1);
        contentTypes.add(contentType2);
        contentTypes.add(contentType3);

        given(contentTypeRepository.findAll()).willReturn(contentTypes);

        List<ContentTypeResponseDto> result = contentTypeService.getContentTypes();

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getName()).isEqualTo(contentType1.getName());
        assertThat(result.get(1).getName()).isEqualTo(contentType2.getName());
        assertThat(result.get(2).getName()).isEqualTo(contentType3.getName());
    }
}
