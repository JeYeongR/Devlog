package com.devlog.post.response;

import java.time.LocalDateTime;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.user.domain.User;

public record PostResponse(

	Long id,

	String title,

	String content,

	VisibilityStatus visibilityStatus,

	String author,

	boolean isMyPost,

	LocalDateTime modifiedAt
) {

	public static PostResponse from(Post post, User user) {
		boolean isMyPost = user != null && post.getUser().getId().equals(user.getId());

		return new PostResponse(
			post.getId(),
			post.getTitle(),
			post.getContent(),
			post.getVisibilityStatus(),
			post.getUser().getNickname(),
			isMyPost,
			post.getModifiedAt()
		);
	}
}
