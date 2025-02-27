package com.devlog.follow.domain;

import java.time.LocalDateTime;

import com.devlog.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "follows")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_id")
	private User follower;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "followed_user_id")
	private User followedUser;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public static Follow create(User follower, User followedUser) {
		Follow follow = new Follow();
		follow.follower = follower;
		follow.followedUser = followedUser;
		follow.createdAt = LocalDateTime.now();
		return follow;
	}
}
