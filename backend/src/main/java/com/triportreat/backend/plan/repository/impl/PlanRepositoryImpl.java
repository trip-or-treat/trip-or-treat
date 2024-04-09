package com.triportreat.backend.plan.repository.impl;

import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_FALSE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.COMING_YN_TRUE;
import static com.triportreat.backend.plan.domain.PlanSearchValue.RECENT_6_MONTH;
import static com.triportreat.backend.plan.domain.PlanSearchValue.SEARCH_TYPE_REGION;
import static com.triportreat.backend.plan.entity.QPlan.plan;
import static com.triportreat.backend.plan.entity.QPlanRegion.planRegion;
import static com.triportreat.backend.region.entity.QRegion.region;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triportreat.backend.plan.domain.PlanRequestDto.PlanSearchRequestDto;
import com.triportreat.backend.plan.domain.PlanResponseDto.PlanListResponseDto;
import com.triportreat.backend.plan.entity.Plan;
import com.triportreat.backend.plan.repository.PlanRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class PlanRepositoryImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlanRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public Page<PlanListResponseDto> searchPlans(PlanSearchRequestDto condition, Pageable pageable, Long userId) {
        List<Plan> result = queryFactory
                .selectFrom(plan)
                .where(userIdEq(userId),
                        titleOrRegionContainsKeyword(condition),
                        dateGoeOrLt(condition.getComingYn()),
                        createdDateInYear(condition.getYear()))
                .orderBy(plan.createdDate.desc(), plan.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<PlanListResponseDto> plans = result.stream()
                .map(PlanListResponseDto::new)
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(plan.count())
                .from(plan)
                .where(userIdEq(userId),
                        titleOrRegionContainsKeyword(condition),
                        dateGoeOrLt(condition.getComingYn()),
                        createdDateInYear(condition.getYear()));

        return PageableExecutionUtils.getPage(plans, pageable, countQuery::fetchOne);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null && userId > 0L ? plan.user.id.eq(userId) : null;
    }

    private BooleanExpression dateGoeOrLt(String comingYn) {
        if (comingYn == null) {
            return null;
        } else if (comingYn.equals(COMING_YN_TRUE)) {
            return plan.startDate.goe(LocalDate.now());
        } else if (comingYn.equals(COMING_YN_FALSE)) {
            return plan.endDate.lt(LocalDate.now());
        }
        return null;
    }

    private BooleanExpression createdDateInYear(int year) {
        if (year == 0) {
            return null;
        } else if (year == RECENT_6_MONTH) {
            return plan.createdDate.between(LocalDateTime.now().minusMonths(6), LocalDateTime.now());
        } else if (year > 0){
            return plan.createdDate.between(LocalDateTime.of(year, 1, 1, 0, 0, 0), LocalDateTime.of(year, 12, 31, 0, 0, 0));
        }
        return null;
    }

    private BooleanExpression titleOrRegionContainsKeyword(PlanSearchRequestDto condition) {
        if (hasText(condition.getType()) && hasText(condition.getKeyword())) {
            return checkSearchType(condition);
        } else {
            return null;
        }
    }

    private BooleanExpression checkSearchType(PlanSearchRequestDto condition) {
        if (condition.getType().equals(SEARCH_TYPE_REGION)) {
            return plan.id.in(
                    JPAExpressions
                            .select(planRegion.plan.id)
                            .from(planRegion)
                            .leftJoin(planRegion.region, region)
                            .where(planRegion.region.name.contains(condition.getKeyword())));
        } else {
            return plan.title.contains(condition.getKeyword());
        }
    }
}
