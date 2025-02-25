package com.devlog.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
