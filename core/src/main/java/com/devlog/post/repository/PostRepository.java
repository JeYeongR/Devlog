package com.devlog.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
