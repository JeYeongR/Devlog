package com.devlog.follow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.follow.domain.Follow;
import com.devlog.user.domain.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByFollowerAndFollowedUser(User follower, User followedUser);

	Optional<Follow> findByFollowerAndFollowedUser(User follower, User followedUser);
}
