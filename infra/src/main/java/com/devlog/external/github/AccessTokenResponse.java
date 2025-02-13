package com.devlog.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(

	@JsonProperty("access_token")
	String accessToken
) {
}
