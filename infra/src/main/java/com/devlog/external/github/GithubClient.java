package com.devlog.external.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GithubClient {

	@Value("${external.github.url}")
	private String githubUrl;

	@Value("${external.github.headers.client-id}")
	private String githubClientId;

	@Value("${external.github.headers.client-secret}")
	private String githubClientSecret;

	private final RestClient restClient;

	public GithubClient() {
		this.restClient = RestClient.create();
	}

	public String getAccessToken(String code) {
		String uri = UriComponentsBuilder.fromHttpUrl(githubUrl)
			.queryParam("client_id", githubClientId)
			.queryParam("client_secret", githubClientSecret)
			.queryParam("code", code)
			.toUriString();

		String body = restClient.get()
			.uri(uri)
			.retrieve()
			.body(String.class);

		// TODO 디비에 유저 정보 저장하고 토큰 발급 해주기
		System.out.println(body);
		return null;
	}
}
