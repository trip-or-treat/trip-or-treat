package com.triportreat.backend.openapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OpenApiRepository {

    @PersistenceContext
    private EntityManager em;

    public void store() {

    }
}
