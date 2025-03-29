package com.devlog.external.github;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

	private final GithubClient githubClient;

	public OauthUserResponse getUserInfo(String code) {
		return githubClient.getUserInfo(code);
	}
}
