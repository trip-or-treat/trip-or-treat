package com.triportreat.backend.region.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.triportreat.backend.region.domain.RegionResponseDto;
import com.triportreat.backend.region.service.RegionService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RegionController.class)
@AutoConfigureMockMvc
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RegionService regionService;

    @Test
    @DisplayName("전체 지역 목록 제공 컨트롤러 메서드 테스트")
    void regions() throws Exception {
        // given
        List<RegionResponseDto> regionResponseDtos = List.of(
                RegionResponseDto.builder().id(1L).name("region1").build(),
                RegionResponseDto.builder().id(2L).name("region2").build(),
                RegionResponseDto.builder().id(3L).name("region3").build()
        );

        // when
        when(regionService.getRegions()).thenReturn(regionResponseDtos);

        // then
        mockMvc.perform(get("/regions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.status", is(200)))
                .andExpect(jsonPath("$.header.message", is("")))
                .andExpect(jsonPath("$.header.result", is(true)))
                .andExpect(jsonPath("$.body", hasSize(3)))
                .andExpect(jsonPath("$.body[0].id", equalTo(1)))
                .andExpect(jsonPath("$.body[0].name", equalTo("region1")))
                .andExpect(jsonPath("$.body[1].id", equalTo(2)))
                .andExpect(jsonPath("$.body[1].name", equalTo("region2")))
                .andExpect(jsonPath("$.body[2].id", equalTo(3)))
                .andExpect(jsonPath("$.body[2].name", equalTo("region3")));
    }
}