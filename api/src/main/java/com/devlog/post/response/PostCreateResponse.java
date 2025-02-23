package com.devlog.post.response;

import com.devlog.post.domain.Post;

public record PostCreateResponse(

	Long postId
) {

	public static PostCreateResponse from(Post post) {
		return new PostCreateResponse(post.getId());
	}
}
