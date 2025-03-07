package com.devlog.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.exception.ApiException;
import com.devlog.user.domain.Token;
import com.devlog.user.repository.TokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenQueryServiceTest {

	@Mock
	TokenRepository tokenRepository;

	@InjectMocks
	TokenQueryService tokenQueryService;

	@Test
	@DisplayName("엑세스 토큰과 리프레쉬 토큰으로 토큰(엔티티) 정상 조회")
	void findTokenTest() {
		// given
		String mockAccessToken = "access";
		String mockRefreshToken = "refresh";
		Token mockToken = mock(Token.class);

		when(tokenRepository.findByAccessTokenAndRefreshToken(mockAccessToken, mockRefreshToken))
			.thenReturn(Optional.of(mockToken));

		// when
		Token result = tokenQueryService.findToken(mockAccessToken, mockRefreshToken);

		// then
		assertThat(result).isEqualTo(mockToken);
		verify(tokenRepository, times(1)).findByAccessTokenAndRefreshToken(mockAccessToken, mockRefreshToken);
	}

	@Test
	@DisplayName("엑세스 토큰과 리프레쉬 토큰으로 존재하지 않는 토큰(엔티티) 조회")
	void findTokenTestNotFound() {
		// given
		String mockAccessToken = "access";
		String mockRefreshToken = "refresh";

		when(tokenRepository.findByAccessTokenAndRefreshToken(mockAccessToken, mockRefreshToken))
			.thenReturn(Optional.empty());

		// when | then
		assertThatThrownBy(() -> tokenQueryService.findToken(mockAccessToken, mockRefreshToken))
			.isInstanceOf(ApiException.class);

		verify(tokenRepository, times(1)).findByAccessTokenAndRefreshToken(mockAccessToken, mockRefreshToken);
	}
}
