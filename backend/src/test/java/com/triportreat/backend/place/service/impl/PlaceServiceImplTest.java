package com.triportreat.backend.place.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.when;

import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.repository.PlaceRepositoryCustom;
import com.triportreat.backend.place.service.PlaceService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
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
}