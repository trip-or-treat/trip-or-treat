package com.triportreat.backend.place.service.impl;

import com.triportreat.backend.common.response.FailMessage;
import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceCommonInfoDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.error.handler.exception.ApiCallFailedException;
import com.triportreat.backend.place.error.handler.exception.ApiResponseParseException;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.PlaceRepositoryCustom;
import com.triportreat.backend.place.service.ExternalApiService;
import com.triportreat.backend.place.service.PlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.mock;

@SpringBootTest
class PlaceServiceImplTest {

    @Autowired
    private PlaceService placeService;

    @MockBean
    private PlaceRepositoryCustom placeRepositoryCustom;

    @MockBean
    private PlaceRepository placeRepository;

    @MockBean
    private ExternalApiService externalApiService;

    @Test
    @DisplayName("검색 조건에 맞는 장소목록 조회")
    void searchPlaceListByCondition() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(1L);
        placeSearchCondition.setKeyword("궁");
        placeSearchCondition.setContentTypeId(14L);
        Pageable pageable = PageRequest.of(0, 10);

        List<PlaceByRegionIdDto> findPlaces = List.of(
                PlaceByRegionIdDto.builder().id(1L).name("경복궁").contentTypeId(14L).build(),
                PlaceByRegionIdDto.builder().id(2L).name("창덕궁").contentTypeId(14L).build(),
                PlaceByRegionIdDto.builder().id(3L).name("덕수궁").contentTypeId(14L).build(),
                PlaceByRegionIdDto.builder().id(4L).name("창경궁").contentTypeId(14L).build(),
                PlaceByRegionIdDto.builder().id(5L).name("경희궁").contentTypeId(14L).build());

        // when
        when(placeRepositoryCustom.searchPlaceListByCondition(placeSearchCondition, pageable))
                .thenReturn(findPlaces);

        // then
        assertThat(findPlaces.size()).isEqualTo(5);
        assertAll(findPlaces.stream().map(place -> () -> {
            assertThat(place.getName()).contains("궁");
            assertThat(place.getContentTypeId()).isEqualTo(14L);
        }));
    }

    @Test
    @DisplayName("공통 정보 조회 테스트")
    void getPlaceCommonInfo() {
        // given
        Long id = 1L;
        ContentType contentType = ContentType.builder().id(1L).name("Type1").build(); // ContentType 객체 생성
        Place place = Place.builder()
                .id(id)
                .name("Test Place")
                .imageOrigin("image.jpg")
                .contentType(contentType)
                .build();

        String overview = "Test Overview";

        PlaceCommonInfoDto expectedPlaceCommonInfoDto = PlaceCommonInfoDto.builder()
                .name("Test Place")
                .imageOrigin("image.jpg")
                .overview("Test Overview")
                .contentTypeId(1L)
                .build();

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(externalApiService.callExternalApiForOverView(id)).thenReturn(overview);

        // then
        PlaceCommonInfoDto actualPlaceCommonInfoDto = placeService.getPlaceCommonInfo(id);

        assertThat(actualPlaceCommonInfoDto).isNotNull();
        assertThat(actualPlaceCommonInfoDto).isEqualToComparingFieldByField(expectedPlaceCommonInfoDto);
    }

    @Test
    @DisplayName("장소 공통정보 조회 실패 테스트 - 장소 정보가 존재하지 않음")
    void getPlaceCommonInfo_notExist() {
        // given
        Long id = 1L;

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        // then
        Exception exception = assertThrows(PlaceNotFoundException.class, () -> placeService.getPlaceCommonInfo(id));
        assertThat(exception.getMessage()).isEqualTo(FailMessage.PLACE_NOT_FOUND.getMessage() + id);
    }

    @Test
    @DisplayName("장소 공통정보 조회 실패 테스트 - 외부 API 호출 실패")
    void getPlaceCommonInfo_ApiCallFailed() {
        // given
        Long id = 1L;
        Place place = mock(Place.class);
        ContentType contentType = mock(ContentType.class);

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(place.getContentType()).thenReturn(contentType);
        when(externalApiService.callExternalApiForOverView(id)).thenThrow(new ApiCallFailedException());

        // then
        Exception exception = assertThrows(ApiCallFailedException.class, () -> placeService.getPlaceCommonInfo(id));
        assertThat(exception.getMessage()).isEqualTo(FailMessage.API_CALL_FAILED.getMessage());
    }

    @Test
    @DisplayName("장소 공통정보 조회 실패 테스트 - 외부 API 응답 처리 실패")
    void getPlaceCommonInfo_ApiResponseParseFailed() {
        // given
        Long id = 1L;
        Place place = mock(Place.class);
        ContentType contentType = mock(ContentType.class);

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(place.getContentType()).thenReturn(contentType);
        when(externalApiService.callExternalApiForOverView(id)).thenThrow(ApiResponseParseException.class);

        // then
        Exception exception = assertThrows(ApiResponseParseException.class, () -> placeService.getPlaceCommonInfo(id));
        assertThat(exception.getMessage()).isEqualTo(FailMessage.API_RESPONSE_PARSE_FAILED.getMessage());
    }
}