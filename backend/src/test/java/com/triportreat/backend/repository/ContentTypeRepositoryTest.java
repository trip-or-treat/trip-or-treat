package com.triportreat.backend.repository;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ContentTypeRepositoryTest {

    @Autowired
    ContentTypeRepository contentTypeRepository;

    @Test
    public void findAll() {
        //given
        //when
        List<ContentType> contentTypes = contentTypeRepository.findAll();

        //then
        Assertions.assertThat(contentTypes.size()).isEqualTo(8);
    }
}
