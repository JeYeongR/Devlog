package com.devlog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlog.domain.Token;
import com.devlog.domain.User;
import com.devlog.external.github.OauthUserResponse;
import com.devlog.external.security.JwtProvider;
import com.devlog.repository.AuthRepository;
import com.devlog.repository.TokenRepository;
import com.devlog.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCommandService {

	private final UserRepository userRepository;
	private final AuthRepository authRepository;
	private final TokenRepository tokenRepository;
	private final JwtProvider jwtProvider;

	@Transactional
	public Token login(String code) {
		OauthUserResponse response = authRepository.getUserInfo(code);

		User user = userRepository.findBySocialProviderId(response.socialProviderId())
			.orElseGet(() -> register(response));

		Token token = createTokens(user);

		user.updateToken(token);

		return tokenRepository.save(token);
	}

	private User register(OauthUserResponse response) {
		return userRepository.save(User.create(response.email(), response.nickname(), response.socialProviderId(),
			response.profileImageUrl()));
	}

	private Token createTokens(User user) {
		Long userId = user.getId();
		String accessToken = jwtProvider.createAccessToken(userId);
		String refreshToken = jwtProvider.createRefreshToken(userId);

		return tokenRepository.save(Token.create(accessToken, refreshToken));
	}
}
