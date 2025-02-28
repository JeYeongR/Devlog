package com.devlog.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.like.domain.Like;
import com.devlog.post.domain.Post;
import com.devlog.user.domain.User;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByUserAndPost(User user, Post post);
}
