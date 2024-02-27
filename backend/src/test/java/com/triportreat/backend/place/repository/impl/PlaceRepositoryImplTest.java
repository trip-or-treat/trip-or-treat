package com.triportreat.backend.place.repository.impl;

import static com.triportreat.backend.place.entity.QContentType.contentType;
import static com.triportreat.backend.place.entity.QSubCategory.subCategory;
import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class PlaceRepositoryImplTest {

    @Autowired
    PlaceRepositoryImpl placeRepositoryImpl;

    @Test
    @DisplayName("장소목록은 최대 페이지 사이즈만큼 조회한다.")
    void searchPlaceListByCondition_page_over() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(1L);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<PlaceByRegionIdDto> placeByRegionIdDtos = placeRepositoryImpl.searchPlaceListByCondition(
                placeSearchCondition, pageable);

        // then
        assertThat(placeByRegionIdDtos.size()).isEqualTo(10);

    }

    @Test
    @DisplayName("장소목록 조회 결과가 페이지 사이즈보다 작다면 조회 결과만큼만 반환한다.")
    void searchPlaceListByCondition_page_less() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(1L);
        placeSearchCondition.setContentTypeId(14L);
        Pageable pageable = PageRequest.of(0, 3);

        // when
        List<PlaceByRegionIdDto> placeByRegionIdDtos = placeRepositoryImpl.searchPlaceListByCondition(
                placeSearchCondition, pageable);

        // then
        assertThat(placeByRegionIdDtos.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("검색 조건중 키워드가 없다면 null을 반환한다.")
    void placeNameContains_no_text() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setKeyword(null);

        // when
        BooleanExpression booleanExpression = placeRepositoryImpl.placeNameContains(placeSearchCondition.getKeyword());

        // then
        assertThat(booleanExpression).isEqualTo(null);
    }

    @Test
    @DisplayName("검색 조건중 키워드가 있다면 동적 쿼리를 반환한다.")
    void subCategoryNameContains_has_text() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setKeyword("키워드");

        // when
        BooleanExpression booleanExpression = placeRepositoryImpl.subCategoryNameContains(
                placeSearchCondition.getKeyword());

        // then
        assertThat(booleanExpression).isEqualTo(subCategory.name.contains(placeSearchCondition.getKeyword()));
    }

    @Test
    @DisplayName("검색 조건중 관광지타입이 있다면 동적쿼리를 반환한다.")
    void contentTypeIdEquals_is_null() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setContentTypeId(12L);

        // when
        BooleanExpression booleanExpression = placeRepositoryImpl.contentTypeIdEquals(placeSearchCondition.getContentTypeId());

        // then
        assertThat(booleanExpression).isEqualTo(contentType.id.eq(placeSearchCondition.getContentTypeId()));
    }
}