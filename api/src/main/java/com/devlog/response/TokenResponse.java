package com.devlog.response;

import com.devlog.domain.Token;

public record TokenResponse(

	String accessToken,

	String refreshToken
) {

	public static TokenResponse from(Token token) {
		return new TokenResponse(token.getAccessToken(), token.getRefreshToken());
	}
}
