package com.triportreat.backend.place.repository.impl;

import static com.triportreat.backend.place.entity.QContentType.contentType;
import static com.triportreat.backend.place.entity.QPlace.place;
import static com.triportreat.backend.place.entity.QSubCategory.subCategory;
import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.place.repository.ContentTypeRepository;
import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.place.repository.SubCategoryRepository;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.repository.RegionRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    ContentTypeRepository contentTypeRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @BeforeEach
    void initData() {
        Region region = Region.builder().id(1L).name("서울").latitude(1.1).longitude(2.2).build();
        regionRepository.save(region);
        ContentType contentType = ContentType.builder().id(14L).name("관광지").build();
        contentTypeRepository.save(contentType);
        ContentType contentType2 = ContentType.builder().id(12L).name("문화시설").build();
        contentTypeRepository.save(contentType2);
        SubCategory subCategory = SubCategory.builder().id("A123456").name("역사").build();
        subCategoryRepository.save(subCategory);
        List<Place> initPlaces = List.of(
                Place.builder().id(1L).name("경복궁").region(region).contentType(contentType).latitude(37.5796).longitude(126.9770).views(0L).subCategory(subCategory).build(),
                Place.builder().id(2L).name("창덕궁").region(region).contentType(contentType).latitude(37.5803).longitude(126.9910).views(0L).subCategory(subCategory).build(),
                Place.builder().id(3L).name("덕수궁").region(region).contentType(contentType).latitude(37.5653).longitude(126.9750).views(0L).subCategory(subCategory).build(),
                Place.builder().id(4L).name("서울숲").region(region).contentType(contentType2).latitude(37.5437).longitude(127.0410).views(0L).subCategory(subCategory).build(),
                Place.builder().id(5L).name("남산").region(region).contentType(contentType2).latitude(37.5502).longitude(126.9900).views(0L).subCategory(subCategory).build()
                );
        placeRepository.saveAll(initPlaces);
    }

    @Test
    @DisplayName("장소목록은 최대 페이지 사이즈만큼 조회한다.")
    void searchPlaceListByCondition_page_over() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(1L);
        Pageable pageable = PageRequest.of(0, 3);

        // when
        List<PlaceByRegionIdDto> findPlaces = placeRepositoryImpl.searchPlaceListByCondition(
                placeSearchCondition, pageable);

        // then
        assertThat(findPlaces.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("장소목록 조회 결과가 페이지 사이즈보다 작다면 조회 결과만큼만 반환한다.")
    void searchPlaceListByCondition_page_less() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setRegionId(1L);
        placeSearchCondition.setContentTypeId(14L);
        Pageable pageable = PageRequest.of(0, 2);

        // when
        List<PlaceByRegionIdDto> placeByRegionIdDtos = placeRepositoryImpl.searchPlaceListByCondition(
                placeSearchCondition, pageable);

        // then
        assertThat(placeByRegionIdDtos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("검색 조건중 키워드가 없다면 null을 반환한다.")
    void placeNameContains_no_text() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition();
        placeSearchCondition.setKeyword(null);

        // when
        BooleanExpression booleanExpression = placeRepositoryImpl.keywordContains(placeSearchCondition.getKeyword());

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
        BooleanExpression booleanExpression = placeRepositoryImpl.keywordContains(
                placeSearchCondition.getKeyword());

        // then
        assertThat(booleanExpression).isEqualTo(
                place.name.contains(placeSearchCondition.getKeyword())
                        .or(subCategory.name.contains(placeSearchCondition.getKeyword())));
    }

    @Test
    @DisplayName("검색 조건중 관광지타입이 있다면 동적 쿼리를 반환한다.")
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