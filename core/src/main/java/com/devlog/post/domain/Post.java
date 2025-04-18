package com.devlog.post.domain;

import java.time.LocalDateTime;

import com.devlog.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	name = "posts",
	indexes = {
		@Index(name = "idx_posts_deleted_visibility_status", columnList = "deleted_at, visibility_status"),
		@Index(name = "idx_posts_like_count", columnList = "like_count")
	}
)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private VisibilityStatus visibilityStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id")
	private User user;

	private long likeCount;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime modifiedAt;

	@Column
	private LocalDateTime deletedAt;

	public static Post create(String title, String content, VisibilityStatus visibilityStatus, User user) {
		Post post = new Post();
		post.title = title;
		post.content = content;
		post.visibilityStatus = visibilityStatus;
		post.user = user;
		post.createdAt = LocalDateTime.now();
		post.modifiedAt = post.createdAt;
		return post;
	}

	public void update(String title, String content, VisibilityStatus visibilityStatus) {
		this.title = title != null ? title : this.title;
		this.content = content != null ? content : this.content;
		this.visibilityStatus = visibilityStatus != null ? visibilityStatus : this.visibilityStatus;
		this.modifiedAt = LocalDateTime.now();
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

	public void addLikeCount() {
		this.likeCount++;
	}

	public void subtractLikeCount() {
		this.likeCount--;
	}
}
