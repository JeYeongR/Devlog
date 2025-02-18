package com.devlog.service;

import org.springframework.stereotype.Service;

import com.devlog.domain.Token;
import com.devlog.external.security.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenIssueService {

	private final JwtProvider jwtProvider;

	public Token createTokens(Long userId) {
		String accessToken = jwtProvider.createAccessToken(userId);
		String refreshToken = jwtProvider.createRefreshToken(userId);

		return Token.create(accessToken, refreshToken);
	}
}
