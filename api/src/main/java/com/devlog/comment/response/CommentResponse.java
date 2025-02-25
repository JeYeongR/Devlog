package com.devlog.comment.response;

import java.time.LocalDateTime;

import com.devlog.comment.domain.Comment;
import com.devlog.user.domain.User;

public record CommentResponse(

	Long id,

	String author,

	String profileImageUrl,

	String content,

	boolean isMyComment,

	LocalDateTime createdAt
) {

	public static CommentResponse from(Comment comment, User user) {
		boolean isMyComment = user != null && comment.getUser().getId().equals(user.getId());

		return new CommentResponse(
			comment.getId(),
			comment.getUser().getNickname(),
			comment.getUser().getProfileImageUrl(),
			comment.getContent(),
			isMyComment,
			comment.getCreatedAt()
		);
	}
}
