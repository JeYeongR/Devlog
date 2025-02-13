package com.devlog.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthUserResponse(

	String login,

	String id,

	@JsonProperty("avatar_url")
	String avatarUrl,

	String email
) {
}
