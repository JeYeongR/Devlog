package com.devlog.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthUserResponse(

	@JsonProperty("login")
	String nickname,

	@JsonProperty("id")
	String socialProviderId,

	@JsonProperty("avatar_url")
	String profileImageUrl,

	String email
) {
}
