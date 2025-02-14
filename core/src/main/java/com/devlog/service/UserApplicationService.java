package com.devlog.service;

import org.springframework.stereotype.Service;

import com.devlog.domain.Token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserApplicationService {

	private final UserCommandService userCommandService;

	public Token login(String code) {
		return userCommandService.login(code);
	}
}
