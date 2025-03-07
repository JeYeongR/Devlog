package com.devlog.like.dto.response;

public record LikeCountResponse(

	int count
) {

	public static LikeCountResponse from(int count) {
		return new LikeCountResponse(count);
	}
}
