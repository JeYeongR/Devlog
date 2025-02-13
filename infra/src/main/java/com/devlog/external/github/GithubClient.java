package com.devlog.external.github;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GithubClient {

	@Value("${external.github.oauth-url}")
	private String githubOauthUrl;

	@Value("${external.github.headers.client-id}")
	private String githubClientId;

	@Value("${external.github.headers.client-secret}")
	private String githubClientSecret;

	@Value("${external.github.api-url}")
	private String githubApiUrl;

	private final RestClient restClient;

	public GithubClient() {
		this.restClient = RestClient.create();
	}

	private String getAccessToken(String code) {
		String uri = UriComponentsBuilder.fromUriString(githubOauthUrl)
			.queryParam("client_id", githubClientId)
			.queryParam("client_secret", githubClientSecret)
			.queryParam("code", code)
			.toUriString();

		AccessTokenResponse response = restClient.get()
			.uri(uri)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(AccessTokenResponse.class);

		return Objects.requireNonNull(response).accessToken();
	}

	public OauthUserResponse getUserInfo(String code) {
		String accessToken = getAccessToken(code);

		String uri = UriComponentsBuilder.fromUriString(githubApiUrl)
			.toUriString();

		return restClient.get()
			.uri(uri)
			.header("Authorization", "token " + accessToken)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(OauthUserResponse.class);
	}
}
