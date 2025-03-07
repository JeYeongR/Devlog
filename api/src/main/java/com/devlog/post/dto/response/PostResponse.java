package com.devlog.post.dto.response;

import java.time.LocalDateTime;

import com.devlog.post.domain.Post;

public record PostResponse(

	Long id,

	String title,

	String author,

	LocalDateTime createdAt
) {

	public static PostResponse from(Post post) {
		return new PostResponse(
			post.getId(),
			post.getTitle(),
			post.getUser().getNickname(),
			post.getCreatedAt()
		);
	}
}
