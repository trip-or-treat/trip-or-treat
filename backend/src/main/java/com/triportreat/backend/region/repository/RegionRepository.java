package com.triportreat.backend.region.repository;

import com.triportreat.backend.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
