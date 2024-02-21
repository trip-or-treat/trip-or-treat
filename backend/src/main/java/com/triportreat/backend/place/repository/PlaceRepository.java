package com.triportreat.backend.place.repository;

import com.triportreat.backend.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    void getTotalPages();
}
