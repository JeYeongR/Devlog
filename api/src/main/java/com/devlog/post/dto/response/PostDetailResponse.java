package com.devlog.post.dto.response;

import java.time.LocalDateTime;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.user.domain.User;

public record PostDetailResponse(

	Long id,

	String title,

	String content,

	VisibilityStatus visibilityStatus,

	String author,

	long likeCount,

	boolean isMyPost,

	LocalDateTime createdAt
) {

	public static PostDetailResponse from(Post post, User user) {
		boolean isMyPost = user != null && post.getUser().getId().equals(user.getId());

		return new PostDetailResponse(
			post.getId(),
			post.getTitle(),
			post.getContent(),
			post.getVisibilityStatus(),
			post.getUser().getNickname(),
			post.getLikeCount(),
			isMyPost,
			post.getCreatedAt()
		);
	}
}
