package com.devlog.post.repository;

import static com.devlog.post.domain.QPost.*;
import static com.devlog.user.domain.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class PostQuerydslRepository {

	private final JPAQueryFactory queryFactory;

	public PostQuerydslRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	public Page<Post> findPostsByCondition(VisibilityStatus visibilityStatus, String query, Pageable pageable) {
		BooleanExpression baseCondition = post.visibilityStatus.eq(visibilityStatus)
			.and(post.deletedAt.isNull());

		BooleanBuilder fullCondition = new BooleanBuilder();

		if (StringUtils.hasLength(query)) {
			BooleanExpression titleMatch = Expressions.numberTemplate(
				Double.class,
				"FUNCTION('MATCH_AGAINST_SINGLE', {0}, {1})",
				post.title, query
			).gt(0);

			BooleanExpression contentMatch = Expressions.numberTemplate(
				Double.class,
				"FUNCTION('MATCH_AGAINST_SINGLE', {0}, {1})",
				post.content, query
			).gt(0);

			fullCondition.or(baseCondition.and(titleMatch.or(contentMatch)));
		} else {
			fullCondition.and(baseCondition);
		}

		List<Post> content = queryFactory
			.selectFrom(post)
			.distinct()
			.leftJoin(post.user, user)
			.fetchJoin()
			.where(fullCondition)
			.orderBy(post.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(post.count())
			.from(post)
			.distinct()
			.where(fullCondition);

		Pageable newPageable = PageRequest.of(pageable.getPageNumber() + 1, pageable.getPageSize(), pageable.getSort());

		return PageableExecutionUtils.getPage(content, newPageable, countQuery::fetchOne);
	}

	public List<Post> findPopular10Posts(VisibilityStatus visibilityStatus) {
		return queryFactory
			.selectFrom(post)
			.distinct()
			.leftJoin(post.user, user)
			.fetchJoin()
			.where(post.visibilityStatus.eq(visibilityStatus)
				.and(post.deletedAt.isNull()))
			.orderBy(post.likes.size().desc())
			.limit(10)
			.fetch();
	}
}
