package com.devlog.like.response;

public record LikeCountResponse(

	int count
) {

	public static LikeCountResponse from(int count) {
		return new LikeCountResponse(count);
	}
}
