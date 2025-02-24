package com.devlog.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAllByVisibilityStatusEqualsAndTitleContainingOrContentContainingOrderByCreatedAtDesc(
		VisibilityStatus visibilityStatus,
		String title,
		String content,
		Pageable pageable
	);
}
