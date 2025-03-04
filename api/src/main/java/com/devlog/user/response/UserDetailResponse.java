package com.devlog.user.response;

import com.devlog.user.domain.User;

public record UserDetailResponse(

	Long id,

	String nickname,

	String profileImageUrl
) {

	public static UserDetailResponse from(User user) {
		return new UserDetailResponse(
			user.getId(),
			user.getNickname(),
			user.getProfileImageUrl()
		);
	}
}
