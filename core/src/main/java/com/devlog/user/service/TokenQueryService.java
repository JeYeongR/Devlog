package com.devlog.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.user.domain.Token;
import com.devlog.user.repository.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenQueryService {

	private final TokenRepository tokenRepository;

	public Token findToken(String accessToken, String refreshToken) {
		return tokenRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken)
			.orElseThrow(() -> new ApiException("토큰을 찾을 수 없습니다.", ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND));
	}
}
