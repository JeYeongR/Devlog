package com.devlog.user.dto.response;

import com.devlog.user.domain.Token;

public record TokenResponse(

	String accessToken,

	String refreshToken
) {

	public static TokenResponse from(Token token) {
		return new TokenResponse(token.getAccessToken(), token.getRefreshToken());
	}
}
