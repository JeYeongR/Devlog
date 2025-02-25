package com.devlog.post.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.devlog.post.domain.Post;

public record PagePostResponse(

	int page,

	int size,

	long totalElements,

	List<PostResponse> contents
) {

	public static PagePostResponse from(Page<Post> pagePost) {
		return new PagePostResponse(
			pagePost.getNumber(),
			pagePost.getSize(),
			pagePost.getTotalElements(),
			pagePost.getContent().stream()
				.map(PostResponse::from)
				.toList()
		);
	}
}
