package com.triportreat.backend.region.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.region.domain.RecommendedPlaceResponseDto;
import com.triportreat.backend.region.domain.RegionDetailResponseDto;
import com.triportreat.backend.region.domain.RegionResponseDto;
import com.triportreat.backend.region.entity.RecommendedPlace;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.repository.RecommendedPlaceRepository;
import com.triportreat.backend.region.repository.RegionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class RegionServiceTest {

    @Autowired
    RegionService regionService;

    @MockBean
    private RegionRepository regionRepository;

    @MockBean
    private RecommendedPlaceRepository recommendedPlaceRepository;

    @Test
    @DisplayName("전체 지역 목록 조회 서비스 메서드 테스트")
    void getRegions() {
        // given
        List<Region> regions = List.of(
                Region.builder().id(1L).name("region1").build(),
                Region.builder().id(2L).name("region2").build(),
                Region.builder().id(3L).name("region3").build()
        );
        given(regionRepository.findAll()).willReturn(regions);

        // when
        List<RegionResponseDto> regionResponseDtos = regionService.getRegions();

        // then
        assertThat(regionResponseDtos.size()).isEqualTo(3);
        assertThat(regionResponseDtos.get(0).getName()).isEqualTo("region1");
        assertThat(regionResponseDtos.get(1).getName()).isEqualTo("region2");
        assertThat(regionResponseDtos.get(2).getName()).isEqualTo("region3");
    }

    @Test
    @DisplayName("지역 상세 정보 조회 서비스 메서드 테스트")
    void getRegionDetail() {
        // given
        Long regionId = 1L;
        Region region = Region.builder()
                .id(regionId)
                .name("지역이름")
                .imageOrigin("이미지저장경로")
                .overview("지역정보")
                .latitude(1.1)
                .longitude(2.2)
                .build();

        Place place = Place.builder()
                .id(1L)
                .build();

        RecommendedPlace recommendedPlace1 = RecommendedPlace.builder()
                .id(1L)
                .region(region)
                .place(place)
                .overview("추천장소1")
                .build();
        RecommendedPlace recommendedPlace2 = RecommendedPlace.builder()
                .id(2L)
                .region(region)
                .place(place)
                .overview("추천장소2")
                .build();
        RecommendedPlace recommendedPlace3 = RecommendedPlace.builder()
                .id(3L)
                .region(region)
                .place(place)
                .overview("추천장소3")
                .build();
        List<RecommendedPlace> recommendedPlaces = List.of(recommendedPlace1, recommendedPlace2, recommendedPlace3);

        given(regionRepository.findById(anyLong())).willReturn(Optional.of(region));
        given(recommendedPlaceRepository.findByRegion(any())).willReturn(recommendedPlaces);

        // when
        RegionDetailResponseDto regionDetail = regionService.getRegionDetail(regionId);
        List<RecommendedPlaceResponseDto> recommendedPlacesDtos = regionDetail.getRecommendedPlaces();

        // then
        assertThat(regionDetail.getId()).isEqualTo(regionId);
        assertThat(regionDetail.getName()).isEqualTo("지역이름");
        assertThat(regionDetail.getImageOrigin()).isEqualTo("이미지저장경로");
        assertThat(regionDetail.getOverview()).isEqualTo("지역정보");
        assertThat(regionDetail.getLatitude()).isEqualTo(1.1);
        assertThat(regionDetail.getLongitude()).isEqualTo(2.2);
        assertThat(recommendedPlacesDtos.size()).isEqualTo(3);
        assertThat(recommendedPlacesDtos.get(0).getId()).isEqualTo(1L);
        assertThat(recommendedPlacesDtos.get(0).getOverview()).isEqualTo("추천장소1");
        assertThat(recommendedPlacesDtos.get(1).getId()).isEqualTo(2L);
        assertThat(recommendedPlacesDtos.get(1).getOverview()).isEqualTo("추천장소2");
        assertThat(recommendedPlacesDtos.get(2).getId()).isEqualTo(3L);
        assertThat(recommendedPlacesDtos.get(2).getOverview()).isEqualTo("추천장소3");
    }
}