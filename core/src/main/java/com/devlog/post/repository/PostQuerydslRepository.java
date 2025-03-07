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
		BooleanBuilder condition = new BooleanBuilder();
		condition.and(post.visibilityStatus.eq(visibilityStatus));
		condition.and(post.deletedAt.isNull());

		if (StringUtils.hasLength(query)) {
			condition.and(post.title.contains(query).or(post.content.contains(query)));
		}

		List<Post> content = queryFactory
			.selectFrom(post)
			.distinct()
			.leftJoin(post.user, user)
			.fetchJoin()
			.where(condition)
			.orderBy(post.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(post.count())
			.from(post)
			.distinct()
			.where(condition);

		Pageable newPageable = PageRequest.of(pageable.getPageNumber() + 1, pageable.getPageSize(), pageable.getSort());

		return PageableExecutionUtils.getPage(content, newPageable, countQuery::fetchOne);
	}
}
