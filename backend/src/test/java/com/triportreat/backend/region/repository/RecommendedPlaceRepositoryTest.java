package com.triportreat.backend.region.repository;

import static org.assertj.core.api.Assertions.*;

import com.triportreat.backend.place.repository.PlaceRepository;
import com.triportreat.backend.region.entity.RecommendedPlace;
import com.triportreat.backend.region.entity.Region;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RecommendedPlaceRepositoryTest {

    @Autowired
    RecommendedPlaceRepository recommendedPlaceRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    RegionRepository regionRepository;

    @Test
    void findByRegion() {
        // given
        Region region = Region.builder()
                .id(1L)
                .name("region")
                .latitude(1.1)
                .longitude(2.2)
                .build();
        regionRepository.save(region);

        RecommendedPlace recommendedPlace1 = RecommendedPlace.builder()
                .id(1L)
                .region(region)
                .overview("추천장소1")
                .build();
        RecommendedPlace recommendedPlace2 = RecommendedPlace.builder()
                .id(2L)
                .region(region)
                .overview("추천장소2")
                .build();
        RecommendedPlace recommendedPlace3 = RecommendedPlace.builder()
                .id(3L)
                .region(region)
                .overview("추천장소3")
                .build();

        recommendedPlaceRepository.save(recommendedPlace1);
        recommendedPlaceRepository.save(recommendedPlace2);
        recommendedPlaceRepository.save(recommendedPlace3);

        // when
        List<RecommendedPlace> recommendedPlaces = recommendedPlaceRepository.findByRegion(region);

        // then
        assertThat(recommendedPlaces.size()).isEqualTo(3);
        assertThat(recommendedPlaces.get(0).getId()).isEqualTo(1);
        assertThat(recommendedPlaces.get(0).getOverview()).isEqualTo("추천장소1");
        assertThat(recommendedPlaces.get(1).getId()).isEqualTo(2);
        assertThat(recommendedPlaces.get(1).getOverview()).isEqualTo("추천장소2");
        assertThat(recommendedPlaces.get(2).getId()).isEqualTo(3);
        assertThat(recommendedPlaces.get(2).getOverview()).isEqualTo("추천장소3");
    }
}