package com.triportreat.backend.scheduler.repository;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.region.entity.Region;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenApiRepository {

    @PersistenceContext
    private final EntityManager em;

    public Region findRegionById(Long id) {
        return em.find(Region.class, id);
    }

    public ContentType findContentTypeById(Long id) {
        return em.find(ContentType.class, id);
    }

    public SubCategory findSubCategoryById(String id) {
        return em.find(SubCategory.class, id);
    }

    public Place findPlaceById(Long id) {
        return em.find(Place.class, id);
    }

    public List<Region> findAllRegion() {
        return em.createQuery("select r from Region r", Region.class)
                .getResultList();
    }

    public List<ContentType> findAllContentType() {
        return em.createQuery("select c from ContentType c", ContentType.class)
                .getResultList();
    }

    public List<SubCategory> findAllSubCategory() {
        return em.createQuery("select s from SubCategory s", SubCategory.class)
                .getResultList();
    }

    public List<Place> findAllPlace() {
        return em.createQuery("select p from Place p", Place.class)
                .getResultList();
    }

    public void update(Place place) {
        if (findPlaceById(place.getId()) == null) {
            em.persist(place);
        }
        else {
            em.merge(place);
        }
    }

}
