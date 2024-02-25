package com.triportreat.backend.contenttype.controller;

import com.triportreat.backend.place.controller.ContentTypeController;
import com.triportreat.backend.place.domain.ContentTypeResponseDto;
import com.triportreat.backend.place.service.ContentTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = ContentTypeController.class)
@AutoConfigureMockMvc
class ContentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ContentTypeService contentTypeService;

    @Test
    void getContentTypes() throws Exception {
        // given
        List<ContentTypeResponseDto> contentTypeResponseDtos = List.of(
                ContentTypeResponseDto.builder().id(1L).name("type1").build(),
                ContentTypeResponseDto.builder().id(2L).name("type2").build(),
                ContentTypeResponseDto.builder().id(3L).name("type3").build()
        );

        // when
        when(contentTypeService.getContentTypes()).thenReturn(contentTypeResponseDtos);

        // then
        mockMvc.perform(get("/places/content_type")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.status", is(200)))
                .andExpect(jsonPath("$.header.message", is("")))
                .andExpect(jsonPath("$.header.result", is(true)))
                .andExpect(jsonPath("$.body", hasSize(3)))
                .andExpect(jsonPath("$.body[0].id", equalTo(1)))
                .andExpect(jsonPath("$.body[0].name", equalTo("type1")))
                .andExpect(jsonPath("$.body[1].id", equalTo(2)))
                .andExpect(jsonPath("$.body[1].name", equalTo("type2")))
                .andExpect(jsonPath("$.body[2].id", equalTo(3)))
                .andExpect(jsonPath("$.body[2].name", equalTo("type3")));
    }
}


