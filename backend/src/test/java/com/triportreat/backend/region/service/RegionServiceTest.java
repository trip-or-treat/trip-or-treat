package com.triportreat.backend.region.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.triportreat.backend.region.domain.RegionResponseDto;
import com.triportreat.backend.region.entity.Region;
import com.triportreat.backend.region.repository.RegionRepository;
import java.util.List;
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
}