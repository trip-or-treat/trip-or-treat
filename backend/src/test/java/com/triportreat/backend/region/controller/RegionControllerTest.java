package com.triportreat.backend.region.controller;

import static com.triportreat.backend.common.response.FailMessage.RECOMMENDED_PLACE_EMPTY;
import static com.triportreat.backend.common.response.FailMessage.REGION_NOT_FOUND;
import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static org.hamcrest.Matchers.equalTo;
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
import com.triportreat.backend.region.domain.RecommendedPlaceResponseDto;
import com.triportreat.backend.region.domain.RegionDetailResponseDto;
import com.triportreat.backend.region.domain.RegionResponseDto;
import com.triportreat.backend.region.error.exception.RecommendedPlacesNotFoundException;
import com.triportreat.backend.region.error.exception.RegionNotFoundException;
import com.triportreat.backend.region.service.RegionService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
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
@WebMvcTest(controllers = RegionController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtExceptionFilter.class,
                        JwtAuthenticationFilter.class,
                        AuthUserArgumentResolver.class,
                        WebConfig.class}))
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
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.message", is("조회에 성공하였습니다.")))
                .andExpect(jsonPath("$.result", is(true)))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].id", equalTo(1)))
                .andExpect(jsonPath("$.data[0].name", equalTo("region1")))
                .andExpect(jsonPath("$.data[1].id", equalTo(2)))
                .andExpect(jsonPath("$.data[1].name", equalTo("region2")))
                .andExpect(jsonPath("$.data[2].id", equalTo(3)))
                .andExpect(jsonPath("$.data[2].name", equalTo("region3")));
    }

    @Test
    @DisplayName("지역 상세 정보 조회 컨트롤러 메서드 테스트")
    void regionDetail() throws Exception {
        // given
        RegionDetailResponseDto regionDetail = RegionDetailResponseDto.builder()
                        .id(1L)
                        .name("지역이름")
                        .recommendedPlaces(List.of(
                                RecommendedPlaceResponseDto.builder().id(1L).name("추천장소1").build(),
                                RecommendedPlaceResponseDto.builder().id(2L).name("추천장소2").build()))
                        .build();

        // when
        when(regionService.getRegionDetail(1L)).thenReturn(regionDetail);

        // then
        mockMvc.perform(get("/regions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(200)))
                .andExpect(jsonPath("$.message", equalTo(GET_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.result", equalTo(true)))
                .andExpect(jsonPath("$.data.id", equalTo(1)))
                .andExpect(jsonPath("$.data.name", equalTo("지역이름")))
                .andExpect(jsonPath("$.data.recommendedPlaces[0].id", equalTo(1)))
                .andExpect(jsonPath("$.data.recommendedPlaces[0].name", equalTo("추천장소1")))
                .andExpect(jsonPath("$.data.recommendedPlaces[1].id", equalTo(2)))
                .andExpect(jsonPath("$.data.recommendedPlaces[1].name", equalTo("추천장소2")));
    }

    @Test
    @DisplayName("지역 상세 정보 조회 컨트롤러 메서드 실행시 지역정보가 없을 경우 예외 발생")
    void regionDetail_RegionNotFoundException() throws Exception {
        // given
        // when
        when(regionService.getRegionDetail(1L)).thenThrow(RegionNotFoundException.class);

        // then
        mockMvc.perform(get("/regions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo(REGION_NOT_FOUND.getMessage())))
                .andExpect(jsonPath("$.result", equalTo(false)))
                .andExpect(jsonPath("$.data", equalTo(null)));
    }

    @Test
    @DisplayName("지역 상세 정보 조회 컨트롤러 메서드 실행시 추천장소정보가 없을 경우 예외 발생")
    void regionDetail_RecommendedPlacesEmptyException() throws Exception {
        // given
        // when
        when(regionService.getRegionDetail(1L)).thenThrow(RecommendedPlacesNotFoundException.class);

        // then
        mockMvc.perform(get("/regions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(500)))
                .andExpect(jsonPath("$.message", equalTo(RECOMMENDED_PLACE_EMPTY.getMessage())))
                .andExpect(jsonPath("$.result", equalTo(false)))
                .andExpect(jsonPath("$.data", equalTo(null)));
    }
}