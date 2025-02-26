package com.devlog.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPostIdAndDeletedAtIsNullOrderByCreatedAtAsc(Long postId);

	Optional<Comment> findByIdAndDeletedAtIsNull(Long id);
}
