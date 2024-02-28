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
        // when
        List<Region> regions = regionRepository.findAll();

        // then
        assertThat(regions.size()).isEqualTo(17);
    }
}