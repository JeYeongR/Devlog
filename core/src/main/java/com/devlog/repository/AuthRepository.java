package com.devlog.repository;

import org.springframework.stereotype.Repository;

import com.devlog.external.github.GithubClient;
import com.devlog.external.github.OauthUserResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

	private final GithubClient githubClient;

	public OauthUserResponse getUserInfo(String code) {
		return githubClient.getUserInfo(code);
	}
}
