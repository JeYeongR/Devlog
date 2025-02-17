package com.devlog.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.domain.Token;
import com.devlog.external.security.JwtProvider;
import com.devlog.repository.TokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenIssueServiceTest {

	@Mock
	TokenRepository tokenRepository;

	@Mock
	JwtProvider jwtProvider;

	@InjectMocks
	TokenIssueService tokenIssueService;

	@Test
	@DisplayName("토큰 정상 생성 및 저장")
	void createTokensTest() {
		// given
		Long testUserId = 1L;

		Token mockToken = mock(Token.class);

		when(tokenRepository.save(any(Token.class))).thenReturn(mockToken);

		when(jwtProvider.createAccessToken(testUserId)).thenReturn("access");
		when(jwtProvider.createRefreshToken(testUserId)).thenReturn("refresh");

		// when
		Token result = tokenIssueService.createTokens(testUserId);

		// then
		assertThat(result).isEqualTo(mockToken);
		verify(tokenRepository, times(1)).save(any(Token.class));
		verify(jwtProvider, times(1)).createAccessToken(testUserId);
		verify(jwtProvider, times(1)).createRefreshToken(testUserId);
	}
}
