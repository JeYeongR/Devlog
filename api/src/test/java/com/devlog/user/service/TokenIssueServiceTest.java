package com.devlog.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.security.JwtProvider;
import com.devlog.user.domain.Token;

@ExtendWith(MockitoExtension.class)
class TokenIssueServiceTest {

	@Mock
	JwtProvider jwtProvider;

	@InjectMocks
	TokenIssueService tokenIssueService;

	@Test
	@DisplayName("토큰 정상 생성 및 저장")
	void createTokensTest() {
		// given
		Long testUserId = 1L;
		String mockAccessToken = "access";
		String mockRefreshToken = "refresh";

		when(jwtProvider.createAccessToken(testUserId)).thenReturn(mockAccessToken);
		when(jwtProvider.createRefreshToken(testUserId)).thenReturn(mockRefreshToken);

		// when
		Token result = tokenIssueService.createTokens(testUserId);

		// then
		assertThat(result.getAccessToken()).isEqualTo(mockAccessToken);
		assertThat(result.getRefreshToken()).isEqualTo(mockRefreshToken);
		verify(jwtProvider, times(1)).createAccessToken(testUserId);
		verify(jwtProvider, times(1)).createRefreshToken(testUserId);
	}
}
