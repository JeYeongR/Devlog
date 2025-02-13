package com.devlog.service;

import org.springframework.stereotype.Service;

import com.devlog.domain.User;
import com.devlog.external.github.OauthUserResponse;
import com.devlog.repository.AuthRepository;
import com.devlog.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCommandService {

	private final UserRepository userRepository;
	private final AuthRepository authRepository;

	public void login(String code) {
		OauthUserResponse response = authRepository.getUserInfo(code);

		userRepository.save(User.create(response.email(), response.login(), response.id(), response.avatarUrl()));

		// TODO: 토큰 생성 기능 구현
	}
}
