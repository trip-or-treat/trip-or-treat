package com.triportreat.backend.place.service.impl;

import static com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Body;
import static com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Items;
import static com.triportreat.backend.place.domain.TourApiPlaceResponseDto.ResponseData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.mock;

import com.triportreat.backend.common.response.FailMessage;
import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceInfoDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.place.error.handler.exception.ApiCallFailedException;
import com.triportreat.backend.place.error.handler.exception.ApiResponseParseException;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.PlaceRepositoryCustom;
import com.triportreat.backend.place.service.ExternalApiService;
import com.triportreat.backend.place.service.PlaceService;
import com.triportreat.backend.region.entity.Region;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

        java.util.List<PlaceByRegionIdDto> findPlaces = List.of(
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
    @DisplayName("장소 공통 정보 조회 테스트")
    void getPlaceInfo() {
        // given
        Long id = 1L;
        ContentType contentType = ContentType.builder().id(1L).name("Type1").build();
        Place place = Place.builder()
                .id(id)
                .name("Test Place")
                .imageThumbnail("image.jpg")
                .contentType(contentType)
                .address("test Address")
                .build();

        String overview = "Test Overview";

        PlaceInfoDto expectedPlaceInfoDto = PlaceInfoDto.builder()
                .name("Test Place")
                .imageThumbnail("image.jpg")
                .overview("Test Overview")
                .contentTypeId(1L)
                .address("test Address")
                .build();

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(externalApiService.callExternalApiForOverView(id)).thenReturn(overview);

        // then
        PlaceInfoDto actualPlaceInfoDto = placeService.getPlaceInfo(id);

        assertThat(actualPlaceInfoDto).isNotNull();
        assertThat(actualPlaceInfoDto).usingRecursiveComparison().isEqualTo(expectedPlaceInfoDto);
    }


    @Test
    @DisplayName("장소 공통정보 조회 실패 테스트 - 장소 정보가 존재하지 않음")
    void getPlaceInfo_notExist() {
        // given
        Long id = 1L;

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        // then
        Exception exception = assertThrows(PlaceNotFoundException.class, () -> placeService.getPlaceInfo(id));
        assertThat(exception.getMessage()).isEqualTo(FailMessage.PLACE_NOT_FOUND.getMessage() + id);
    }

    @Test
    @DisplayName("장소 공통정보 조회 실패 테스트 - 외부 API 호출 실패")
    void getPlaceInfo_ApiCallFailed() {
        // given
        Long id = 1L;
        Place place = mock(Place.class);
        ContentType contentType = mock(ContentType.class);

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(place.getContentType()).thenReturn(contentType);
        when(externalApiService.callExternalApiForOverView(id)).thenThrow(new ApiCallFailedException());

        // then
        Exception exception = assertThrows(ApiCallFailedException.class, () -> placeService.getPlaceInfo(id));
        assertThat(exception.getMessage()).isEqualTo(FailMessage.API_CALL_FAILED.getMessage());
    }

    @Test
    @DisplayName("장소 공통정보 조회 실패 테스트 - 외부 API 응답 처리 실패")
    void getPlaceInfo_ApiResponseParseFailed() {
        // given
        Long id = 1L;
        Place place = mock(Place.class);
        ContentType contentType = mock(ContentType.class);

        // when
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(place.getContentType()).thenReturn(contentType);
        when(externalApiService.callExternalApiForOverView(id)).thenThrow(ApiResponseParseException.class);

        // then
        Exception exception = assertThrows(ApiResponseParseException.class, () -> placeService.getPlaceInfo(id));
        assertThat(exception.getMessage()).isEqualTo(FailMessage.API_RESPONSE_PARSE_FAILED.getMessage());
    }

    @Nested
    @DisplayName("장소 데이터 업데이트")
    class UpdatePlacesByExternalApi {

        private List<Item> initTourApiResponse() {
            TourApiPlaceResponseDto dto = new TourApiPlaceResponseDto();

            ResponseData responseData = new ResponseData();
            dto.setResponse(responseData);

            Body body = new Body();
            responseData.setBody(body);

            Items items = new Items();
            body.setItems(items);

            Item item1 = Item.builder()
                    .id(1L)
                    .regionId(1L)
                    .contentTypeId(1L)
                    .subCategoryId("123")
                    .name("업데이트할 장소 이름")
                    .mainCategoryId("A01")
                    .midCategoryId("B01")
                    .latitude(37.498095)
                    .longitude(127.027610)
                    .build();

            Item item2 = Item.builder()
                    .id(2L)
                    .regionId(1L)
                    .contentTypeId(1L)
                    .subCategoryId("123")
                    .name("새로 생성한 장소 이름")
                    .mainCategoryId("A01")
                    .midCategoryId("B01")
                    .latitude(37.498095)
                    .longitude(127.027610)
                    .build();

            items.setItem(List.of(item1, item2));
            return dto.getResponse().getBody().getItems().getItem();
        }


        @Test
        @DisplayName("장소가 이미 존재한다면 업데이트한다.")
        void updateExistingPlaces() {
            // given
            List<Item> items = initTourApiResponse();
            Item placeByTourApi = items.get(0);

            Place place = Place.builder()
                    .id(1L)
                    .name("기존 장소 이름")
                    .latitude(37.498095)
                    .longitude(127.027610)
                    .views(10L)
                    .build();

            when(placeRepository.findById(1L)).thenReturn(Optional.ofNullable(place));

            // when
            Optional<Place> existingPlace = placeRepository.findById(placeByTourApi.getId());
            existingPlace.ifPresent(p -> p.updateByTourApi(placeByTourApi));

            // then
            assertThat(existingPlace.get().getName()).isEqualTo("업데이트할 장소 이름");
            assertThat(existingPlace.get().getViews()).isEqualTo(10L);

        }

        @Test
        @DisplayName("장소가 존재하지 않는다면 새로 추가한다.")
        void createNewPlaces() {
            // given
            List<Item> items = initTourApiResponse();
            Item newPlaceByTourApi = items.get(1);

            Region region = Region.builder().id(1L).name("지역 테스트").build();
            ContentType contentType = ContentType.builder().id(1L).name("관광지타입 테스트").build();
            SubCategory subCategory = SubCategory.builder().id("A1234").name("소분류 테스트").build();

            when(placeRepository.findById(2L))
                    .thenReturn(Optional.empty());

            // when
            Optional<Place> place = placeRepository.findById(newPlaceByTourApi.getId());
            Place newPlace = Item.toEntity(newPlaceByTourApi, region, contentType, subCategory);
            placeRepository.save(newPlace);

            //then
            assertThat(newPlace.getName()).isEqualTo("새로 생성한 장소 이름");
        }

        @Test
        @DisplayName("외래키 필드가 하나라도 null이면 저장하지 않는다.")
        void notSaveIfForeignKeyIsNull() {
            // given
            List<Item> items = initTourApiResponse();
            Item placeByTourApi = items.get(0);

            when(placeRepository.findById(1L)).thenReturn(Optional.empty());

            // when
            Place newPlace = Item.toEntity(placeByTourApi, null, null, null);
            placeRepository.save(newPlace);
            Optional<Place> savedNewPlace = placeRepository.findById(newPlace.getId());

            // then
            assertThat(savedNewPlace).isEmpty();
        }

        @Test
        @DisplayName("관광지타입이 축제공연행사나 여행코스이면 저장하지 않는다")
        void notSaveIfContentTypeIdIsFestivalOrCourse() {
            // given
            List<Item> items = initTourApiResponse();
            Item placeByTourApi = items.get(0);

            Region region = Region.builder().id(1L).name("지역 테스트").build();
            ContentType contentType = ContentType.builder().id(15L).name("관광지타입 테스트").build();
            SubCategory subCategory = SubCategory.builder().id("A1234").name("소분류 테스트").build();

            when(placeRepository.findById(1L)).thenReturn(Optional.empty());

            // when
            Place newPlace = Item.toEntity(placeByTourApi, region, contentType, subCategory);
            if (!contentType.getId().equals(15L) && !contentType.getId().equals(25L)) {
                placeRepository.save(newPlace);
            }
            Optional<Place> savedNewPlace = placeRepository.findById(newPlace.getId());

            // then
            assertThat(savedNewPlace).isEmpty();
        }

    }
}