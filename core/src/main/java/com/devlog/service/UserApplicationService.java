package com.devlog.service;

import org.springframework.stereotype.Service;

import com.devlog.external.github.GithubClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserApplicationService {

	private final GithubClient githubClient;

	public void login(String code) {
		System.out.println("code: " + code);
		githubClient.getAccessToken(code);
	}
}
