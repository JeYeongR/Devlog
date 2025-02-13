package com.devlog.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(length = 20, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String socialProviderId;

	@Column(nullable = false)
	private String profileImageUrl;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime modifiedAt;

	public static User create(String email, String nickname, String socialProviderId, String profileImageUrl) {
		User user = new User();
		user.email = email;
		user.role = UserRole.USER;
		user.nickname = nickname;
		user.socialProviderId = socialProviderId;
		user.profileImageUrl = profileImageUrl;
		user.createdAt = LocalDateTime.now();
		user.modifiedAt = user.createdAt;
		return user;
	}
}
