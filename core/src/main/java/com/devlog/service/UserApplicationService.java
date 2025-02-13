package com.devlog.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserApplicationService {

	private final UserCommandService userCommandService;

	public void login(String code) {
		userCommandService.login(code);
	}
}
