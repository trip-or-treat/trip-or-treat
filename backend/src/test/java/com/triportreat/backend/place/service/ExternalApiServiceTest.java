package com.triportreat.backend.place.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.triportreat.backend.place.domain.TourApiPlaceResponseDto;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Body;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Items;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.ResponseData;
import com.triportreat.backend.place.error.handler.exception.ApiCallFailedException;
import com.triportreat.backend.place.error.handler.exception.ApiResponseParseException;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ExternalApiServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Nested
    @DisplayName("장소 업데이트용 외부 API 호출")
    class callExternalApiForUpdatePlaces {

        private TourApiPlaceResponseDto initTourApiResponse() {
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
            return dto;
        }

        @Test
        @DisplayName("응답 데이터 포맷이 변경된 경우 예외가 발생한다.")
        void responseIsNull() {
            // given
            URI requestUrl = URI.create("testUrl");

            // when
            when(restTemplate.getForEntity(requestUrl, TourApiPlaceResponseDto.class))
                    .thenThrow(ApiResponseParseException.class);

            // then
            assertThatThrownBy(() -> restTemplate.getForEntity(requestUrl, TourApiPlaceResponseDto.class))
                    .isInstanceOf(ApiResponseParseException.class);
        }

        @Test
        @DisplayName("호출이 실패하면 예외가 발생한다.")
        void callFailed() {
            // given
            URI requestUrl = URI.create("testUrl");

            // when
            when(restTemplate.getForEntity(requestUrl, TourApiPlaceResponseDto.class))
                    .thenThrow(ApiCallFailedException.class);

            // then
            assertThatThrownBy(() -> restTemplate.getForEntity(requestUrl, TourApiPlaceResponseDto.class))
                    .isInstanceOf(ApiCallFailedException.class);
        }

        @Test
        @DisplayName("응답이 성공하면 장소 목록을 리스트로 반환한다.")
        void success() {
            // given
            URI requestUrl = URI.create("testUrl");
            TourApiPlaceResponseDto responseDto = initTourApiResponse();
            List<Item> places = responseDto.getResponse().getBody().getItems().getItem();

            // when
            when(restTemplate.getForEntity(requestUrl, TourApiPlaceResponseDto.class))
                    .thenReturn(ResponseEntity.ok(responseDto));

            // then
            assertThat(places.size()).isEqualTo(2);
        }
    }
}