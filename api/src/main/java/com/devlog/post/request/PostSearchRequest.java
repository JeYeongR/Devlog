package com.devlog.post.request;

public record PostSearchRequest(

	String query,

	int page,

	int size
) {
}
