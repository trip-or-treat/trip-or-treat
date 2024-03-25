package com.triportreat.backend.place.repository;

import com.triportreat.backend.place.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPlaceId(Long id, Pageable pageable);
    List<Review> findByUserId(Long userId, Pageable pageable);
}