package com.triportreat.backend.region.repository;

import com.triportreat.backend.region.entity.RecommendedPlace;
import com.triportreat.backend.region.entity.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedPlaceRepository extends JpaRepository<RecommendedPlace, Long> {
    List<RecommendedPlace> findByRegion(Region region);
}
