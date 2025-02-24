package com.devlog.post.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAllByVisibilityStatusEqualsAndTitleContainingOrContentContainingAndDeletedAtIsNullOrderByCreatedAtDesc(
		VisibilityStatus visibilityStatus,
		String title,
		String content,
		Pageable pageable
	);

	Optional<Post> findByIdAndDeletedAtIsNull(Long id);
}
