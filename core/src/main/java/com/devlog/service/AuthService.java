package com.devlog.service;

import org.springframework.stereotype.Service;

import com.devlog.external.github.GithubClient;
import com.devlog.external.github.OauthUserResponse;

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
