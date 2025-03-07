package com.devlog.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findByIdAndDeletedAtIsNull(Long id);
}
