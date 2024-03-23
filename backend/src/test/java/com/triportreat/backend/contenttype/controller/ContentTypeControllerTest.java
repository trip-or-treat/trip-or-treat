package com.triportreat.backend.contenttype.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.triportreat.backend.auth.filter.JwtAuthenticationFilter;
import com.triportreat.backend.auth.filter.JwtExceptionFilter;
import com.triportreat.backend.auth.utils.AuthUserArgumentResolver;
import com.triportreat.backend.common.config.WebConfig;
import com.triportreat.backend.place.controller.ContentTypeController;
import com.triportreat.backend.place.domain.ContentTypeResponseDto;
import com.triportreat.backend.place.service.ContentTypeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ContentTypeController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtExceptionFilter.class,
                        JwtAuthenticationFilter.class,
                        AuthUserArgumentResolver.class,
                        WebConfig.class}))
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
        mockMvc.perform(get("/places/content-type")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)))
                .andExpect(jsonPath("$.message", is("조회에 성공하였습니다.")))
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("type1")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("type2")))
                .andExpect(jsonPath("$.data[2].id", is(3)))
                .andExpect(jsonPath("$.data[2].name", is("type3")));
    }
}
