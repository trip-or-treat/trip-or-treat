package com.triportreat.backend.region.repository;

import static org.assertj.core.api.Assertions.*;

import com.triportreat.backend.region.entity.Region;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RegionRepositoryTest {

    @Autowired
    RegionRepository regionRepository;

    @Test
    @DisplayName("findAll() 메서드 테스트")
    void findAll() {
        // given
        Region region1 = Region.builder()
                .id(1L)
                .name("region1")
                .latitude(1.1)
                .longitude(2.2)
                .build();
        Region region2 = Region.builder()
                .id(2L)
                .name("region2")
                .latitude(1.1)
                .longitude(2.2)
                .build();
        regionRepository.save(region1);
        regionRepository.save(region2);

        // when
        List<Region> regions = regionRepository.findAll();

        // then
        assertThat(regions.size()).isEqualTo(2);
    }
}