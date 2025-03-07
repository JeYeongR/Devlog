package com.devlog.post.dto.request;

public record PostSearchRequest(

	String query,

	int page,

	int size
) {
}
