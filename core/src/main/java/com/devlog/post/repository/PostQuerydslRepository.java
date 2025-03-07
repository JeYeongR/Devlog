package com.devlog.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.QPost;
import com.devlog.post.domain.VisibilityStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class PostQuerydslRepository {

	private final JPAQueryFactory queryFactory;

	public PostQuerydslRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	public Page<Post> findPostsByCondition(VisibilityStatus visibilityStatus, String query, Pageable pageable) {
		QPost post = QPost.post;

		BooleanBuilder condition = new BooleanBuilder();
		condition.and(post.visibilityStatus.eq(visibilityStatus));
		condition.and(post.deletedAt.isNull());

		if (query != null && !query.isEmpty()) {
			condition.and(post.title.contains(query).or(post.content.contains(query)));
		}

		List<Post> posts = queryFactory
			.selectDistinct(post)
			.from(post)
			.where(condition)
			.orderBy(post.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long totalCount = queryFactory
			.selectDistinct(post)
			.from(post)
			.where(condition)
			.fetchCount();

		Pageable newPageable = PageRequest.of(pageable.getPageNumber() + 1, pageable.getPageSize(), pageable.getSort());

		return new PageImpl<>(posts, newPageable, totalCount);
	}
}
