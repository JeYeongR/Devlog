package com.devlog.post.dto.response;

import com.devlog.post.domain.Post;

public record PostUpdateResponse(

	Long postId
) {

	public static PostUpdateResponse from(Post post) {
		return new PostUpdateResponse(post.getId());
	}
}
