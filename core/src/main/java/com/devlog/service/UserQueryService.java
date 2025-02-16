package com.devlog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devlog.domain.User;
import com.devlog.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserQueryService {

	private final UserRepository userRepository;

	public Optional<User> findUser(String socialProviderId) {
		return userRepository.findBySocialProviderId(socialProviderId);
	}
}
