package com.triportreat.backend.place.repository;

import com.triportreat.backend.place.entity.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
}
