package com.triportreat.backend.plan.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_FALSE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_TRUE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.RECENT_6_MONTH;
import static com.triportreat.backend.plan.domain.PlanSearchValue.SEARCH_TYPE_REGION;
import static com.triportreat.backend.plan.domain.PlanSearchValue.SEARCH_TYPE_TITLE;
import static com.triportreat.backend.plan.entity.QPlan.plan;
import static com.triportreat.backend.plan.entity.QPlanRegion.planRegion;
import static com.triportreat.backend.region.entity.QRegion.region;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import com.triportreat.backend.plan.repository.PlanRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@Slf4j
public class PlanRepositoryImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlanRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public Page<PlanListResponseDto> searchPlans(PlanSearchRequestDto condition, Pageable pageable, Long userId) {
        return isSearchByTitle(condition) ? searchPlansByTitle(condition, pageable, userId) : searchPlansByRegion(condition, pageable, userId);
    }

    private Page<PlanListResponseDto> searchPlansByTitle(PlanSearchRequestDto condition, Pageable pageable, Long userId) {
        log.info("제목검색");

        List<Long> planIds = queryFactory
                .select(plan.id)
                .from(plan)
                .where(
                        userEq(userId),
                        containsKeyword(condition.getType(), condition.getKeyword()),
                        planDateGoeOrLt(condition.getComingYn()),
                        planCreatedInYear(condition.getYear())
                )
                .orderBy(plan.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(plan.id.count())
                .from(plan)
                .where(
                        userEq(userId),
                        containsKeyword(condition.getType(), condition.getKeyword()),
                        planDateGoeOrLt(condition.getComingYn()),
                        planCreatedInYear(condition.getYear())
                );

        return PageableExecutionUtils.getPage(search(planIds), pageable, () -> countQuery.fetch().get(0));
    }

    private Page<PlanListResponseDto> searchPlansByRegion(PlanSearchRequestDto condition, Pageable pageable, Long userId) {
        log.info("지역검색");

        List<Long> planIds = queryFactory
                .select(planRegion.plan.id)
                .from(planRegion)
                .where(planRegion.region.id.in(
                                JPAExpressions
                                        .select(region.id)
                                        .from(region)
                                        .where(containsKeyword(condition.getType(), condition.getKeyword()))),
                        planDateGoeOrLt(condition.getComingYn()),
                        planCreatedInYear(condition.getYear()),
                        planRegionUserIdEq(userId))
                .orderBy(planRegion.plan.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(planRegion.plan.count())
                .from(planRegion)
                .where(planRegion.region.id.in(
                                JPAExpressions
                                        .select(region.id)
                                        .from(region)
                                        .where(containsKeyword(condition.getType(), condition.getKeyword()))
                        ),
                        planDateGoeOrLt(condition.getComingYn()),
                        planCreatedInYear(condition.getYear()),
                        planRegionUserIdEq(userId));

        return PageableExecutionUtils.getPage(search(planIds), pageable, () -> countQuery.fetch().get(0));
    }

    private List<PlanListResponseDto> search(List<Long> planIds) {
        return planIds.stream().map(planId -> queryFactory
                        .selectFrom(plan)
                        .leftJoin(plan.regions, planRegion)
                        .leftJoin(planRegion.region, region)
                        .where(plan.id.eq(planId))
                        .transform(groupBy(plan.id).list(Projections.fields(PlanListResponseDto.class,
                                plan.id.as("planId"),
                                plan.title,
                                plan.startDate,
                                plan.endDate,
                                plan.createdDate.as("createdDateTime"),
                                list(region.name).as("regions")))).get(0))
                .collect(Collectors.toList());
    }

    private BooleanExpression userEq(Long userId) {
        return userId!= null && userId > 0L ? plan.user.id.eq(userId) : null;
    }

    private BooleanExpression planRegionUserIdEq(Long userId) {
        return userId != null && userId > 0L ? planRegion.plan.user.id.eq(userId) : null;
    }

    private BooleanExpression planDateGoeOrLt(String comingYn) {
        if (!hasText(comingYn) || comingYn.equals(COMING_YN_TRUE)) {
            return plan.startDate.goe(LocalDate.now());
        } else if (comingYn.equals(COMING_YN_FALSE)) {
            return plan.endDate.lt(LocalDate.now());
        } else {
            return null;
        }
    }

    private BooleanExpression planCreatedInYear(int year) {
        if (year == RECENT_6_MONTH) {
            return plan.createdDate.between(LocalDateTime.now().minusMonths(6), LocalDateTime.now());
        } else {
            return plan.createdDate.between(LocalDateTime.of(year, 1, 1, 0, 0, 0), LocalDateTime.of(year, 12, 31, 0, 0, 0));
        }
    }

    private BooleanExpression containsKeyword(String type, String keyword) {
        if (!hasText(type) || type.equals(SEARCH_TYPE_TITLE)) {
            return titleContainsKeyword(keyword);
        } else if (type.equals(SEARCH_TYPE_REGION)){
            return regionNameContainsKeyword(keyword);
        }
        return null;
    }

    private BooleanExpression titleContainsKeyword(String keyword) {
        return hasText(keyword) ? plan.title.contains(keyword) : null;
    }

    private BooleanExpression regionNameContainsKeyword(String keyword) {
        return hasText(keyword) ? region.name.contains(keyword) : null;
    }

    private boolean isSearchByTitle(PlanSearchRequestDto condition) {
        return !condition.getType().equals(SEARCH_TYPE_REGION) || !hasText(condition.getKeyword());
    }
}
