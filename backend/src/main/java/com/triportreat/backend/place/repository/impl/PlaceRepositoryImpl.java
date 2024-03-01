package com.triportreat.backend.place.repository.impl;

import static com.triportreat.backend.place.entity.QContentType.contentType;
import static com.triportreat.backend.place.entity.QPlace.place;
import static com.triportreat.backend.place.entity.QSubCategory.subCategory;
import static com.triportreat.backend.region.entity.QRegion.region;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triportreat.backend.place.domain.PlaceByRegionIdDto;
import com.triportreat.backend.place.domain.PlaceSearchCondition;
import com.triportreat.backend.place.domain.QPlaceByRegionIdDto;
import com.triportreat.backend.place.repository.PlaceRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlaceRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PlaceByRegionIdDto> searchPlaceListByCondition(PlaceSearchCondition placeSearchCondition, Pageable pageable) {
        return queryFactory
                .select(new QPlaceByRegionIdDto(
                        place.id,
                        place.name,
                        place.imageThumbnail,
                        subCategory.name,
                        place.latitude,
                        place.longitude,
                        contentType.id))
                .from(place)
                .leftJoin(place.subCategory, subCategory)
                .where(
                        region.id.eq(placeSearchCondition.getRegionId()),
                        contentTypeIdEquals(placeSearchCondition.getContentTypeId()),
                        placeNameContains(placeSearchCondition.getKeyword()),
                        subCategoryNameContains(placeSearchCondition.getKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public BooleanExpression placeNameContains(String keyword) {
        if (hasText(keyword)) {
            return place.name.contains(keyword);
        }
        return null;
    }

    public BooleanExpression subCategoryNameContains(String keyword) {
        if (hasText(keyword)) {
            return subCategory.name.contains(keyword);
        }
        return null;
    }

    public BooleanExpression contentTypeIdEquals(Long contentTypeId) {
        if (contentTypeId != null) {
            return contentType.id.eq(contentTypeId);
        }
        return null;
    }
}
