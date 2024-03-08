package com.triportreat.backend.place.controller;

import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceCommonInfoDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.service.PlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.triportreat.backend.common.response.FailMessage.GET_FAIL;
import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;

    @Test
    @DisplayName("검색 조건중 지역ID가 null이면 응답은 실패한다.")
    void searchPlaceListByCondition_fail() throws Exception {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(null);

        List<PlaceByRegionIdDto> places = List.of(
                PlaceByRegionIdDto.builder().id(1L).name("테스트1").contentTypeId(1L).build(),
                PlaceByRegionIdDto.builder().id(2L).name("테스트2").contentTypeId(2L).build(),
                PlaceByRegionIdDto.builder().id(3L).name("테스트3").contentTypeId(3L).build()
        );

        // when
        when(placeService.searchPlaceListByCondition(placeSearchCondition, Pageable.ofSize(10))).thenReturn(places);

        // then
        mockMvc.perform(get("/places")
                .param("regionId", String.valueOf(placeSearchCondition.getRegionId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(GET_FAIL.getMessage()))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.data").isMap());
    }

    @Test
    @DisplayName("검색 조건중 지역ID가 null이 아니면 응답은 성공한다")
    void searchPlaceListByCondition_success() throws Exception {
        // given
        List<PlaceByRegionIdDto> places = List.of(
                PlaceByRegionIdDto.builder().id(1L).name("테스트1").contentTypeId(1L).build(),
                PlaceByRegionIdDto.builder().id(2L).name("테스트2").contentTypeId(2L).build(),
                PlaceByRegionIdDto.builder().id(3L).name("테스트3").contentTypeId(3L).build()
        );

        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(1L);

        // when
        when(placeService.searchPlaceListByCondition(placeSearchCondition, Pageable.ofSize(10))).thenReturn(places);

        // then
        mockMvc.perform(get("/places")
                .param("regionId", String.valueOf(placeSearchCondition.getRegionId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("테스트1"))
                .andExpect(jsonPath("$.data[0].contentTypeId").value(1));
    }

    @Test
    @DisplayName("장소목록 조회 결과가 없으면 빈 리스트를 반환한다.")
    void searchPlaceListByCondition_empty() throws Exception {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(123L);
        placeSearchCondition.setKeyword("테스트");
        placeSearchCondition.setContentTypeId(123L);

        List<PlaceByRegionIdDto> places = List.of(
                PlaceByRegionIdDto.builder().id(1L).name("테스트1").contentTypeId(1L).build(),
                PlaceByRegionIdDto.builder().id(2L).name("테스트2").contentTypeId(2L).build(),
                PlaceByRegionIdDto.builder().id(3L).name("테스트3").contentTypeId(3L).build()
        );

        // when
        when(placeService.searchPlaceListByCondition(placeSearchCondition, Pageable.ofSize(10))).thenReturn(places);

        // then
        mockMvc.perform(get("/places")
                .param("regionId", String.valueOf(placeSearchCondition.getRegionId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("특정 장소의 공통 정보를 조회하면 응답은 성공한다.")
    void getPlaceCommonInfo_success() throws Exception {
        // given
        Long id = 1L;
        PlaceCommonInfoDto placeCommonInfoDto = PlaceCommonInfoDto.builder()
                .name("Test Place")
                .imageThumbnail("image.jpg")
                .overview("Test Overview")
                .contentTypeId(1L)
                .address("Test Address")
                .build();

        // when
        when(placeService.getPlaceCommonInfo(id)).thenReturn(placeCommonInfoDto);

        // then
        mockMvc.perform(get("/places/" + id + "/info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.name").value(placeCommonInfoDto.getName()))
                .andExpect(jsonPath("$.data.imageThumbnail").value(placeCommonInfoDto.getImageThumbnail()))
                .andExpect(jsonPath("$.data.overview").value(placeCommonInfoDto.getOverview()))
                .andExpect(jsonPath("$.data.contentTypeId").value(placeCommonInfoDto.getContentTypeId()))
                .andExpect(jsonPath("$.data.address").value(placeCommonInfoDto.getAddress()));
    }
}