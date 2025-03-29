package com.devlog.post.dto.response;

import com.devlog.post.domain.PostDocument;

public record PostResponse(

	Long id,

	String title,

	String author,

	long likeCount,

	String createdAt
) {

	public static PostResponse from(PostDocument document) {
		return new PostResponse(
			document.getPostId(),
			document.getTitle(),
			document.getUserName(),
			document.getLikeCount(),
			document.getCreatedAt()
		);
	}
}
