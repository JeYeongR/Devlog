package com.devlog.service;

import org.springframework.stereotype.Service;

import com.devlog.domain.Token;
import com.devlog.domain.User;
import com.devlog.external.security.JwtProvider;
import com.devlog.repository.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenIssueService {

	private final TokenRepository tokenRepository;
	private final JwtProvider jwtProvider;

	public Token createTokens(User user) {
		Long userId = user.getId();
		String accessToken = jwtProvider.createAccessToken(userId);
		String refreshToken = jwtProvider.createRefreshToken(userId);

		return tokenRepository.save(Token.create(accessToken, refreshToken));
	}
}
