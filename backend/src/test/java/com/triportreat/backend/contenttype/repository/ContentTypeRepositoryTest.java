package com.triportreat.backend.contenttype.repository;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ContentTypeRepositoryTest {

    @Autowired
    ContentTypeRepository contentTypeRepository;

    @Test
    public void findAll() {
        //given
        ContentType contentType1 = ContentType.builder()
                .id(1L)
                .name("aType")
                .build();

        ContentType contentType2 = ContentType.builder()
                .id(2L)
                .name("bType")
                .build();
        contentTypeRepository.save(contentType1);
        contentTypeRepository.save(contentType2);

        //when
        List<ContentType> contentTypes = contentTypeRepository.findAll();

        //then
        assertThat(contentTypes.size()).isEqualTo(2);
    }
}