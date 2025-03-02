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

import com.devlog.external.github.OauthUserResponse;
import com.devlog.user.domain.Token;
import com.devlog.user.domain.User;
import com.devlog.user.response.TokenResponse;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

	@Mock
	private UserCommandService userCommandService;

	@Mock
	UserQueryService userQueryService;

	@Mock
	AuthService authService;

	@Mock
	TokenIssueService tokenIssueService;

	@InjectMocks
	UserApplicationService userApplicationService;

	@Test
	@DisplayName("신규 사용자 로그인")
	void loginTest() {
		// given
		String code = "test-code";

		OauthUserResponse response = mock(OauthUserResponse.class);
		when(response.socialProviderId()).thenReturn("test-social-id");

		User mockUser = mock(User.class);
		when(mockUser.getId()).thenReturn(1L);

		Token mockToken = mock(Token.class);
		TokenResponse mockTokenResponse = TokenResponse.from(mockToken);

		when(authService.getUserInfo(code)).thenReturn(response);
		when(userQueryService.findUser(response.socialProviderId())).thenReturn(Optional.empty());
		when(userCommandService.save(any(User.class))).thenReturn(mockUser);
		when(tokenIssueService.createTokens(mockUser.getId())).thenReturn(mockToken);

		// when
		TokenResponse result = userApplicationService.login(code);

		// then
		assertThat(result).isEqualTo(mockTokenResponse);
		verify(authService, times(1)).getUserInfo(code);
		verify(userQueryService, times(1)).findUser(response.socialProviderId());
		verify(userCommandService, times(1)).save(any(User.class));
		verify(tokenIssueService, times(1)).createTokens(mockUser.getId());
	}

	@Test
	@DisplayName("기존 사용자 로그인")
	void loginTestExistingUser() {
		// given
		String code = "test-code";

		OauthUserResponse response = mock(OauthUserResponse.class);
		when(response.socialProviderId()).thenReturn("test-social-id");

		User mockUser = mock(User.class);
		when(mockUser.getId()).thenReturn(1L);

		Token mockToken = mock(Token.class);
		TokenResponse mockTokenResponse = TokenResponse.from(mockToken);

		when(authService.getUserInfo(code)).thenReturn(response);
		when(userQueryService.findUser(response.socialProviderId())).thenReturn(Optional.of(mockUser));
		when(tokenIssueService.createTokens(mockUser.getId())).thenReturn(mockToken);

		// when
		TokenResponse result = userApplicationService.login(code);

		// then
		assertThat(result).isEqualTo(mockTokenResponse);
		verify(authService, times(1)).getUserInfo(code);
		verify(userQueryService, times(1)).findUser(response.socialProviderId());
		verify(userCommandService, never()).save(any());
		verify(tokenIssueService, times(1)).createTokens(1L);
	}

	@Test
	@DisplayName("사용자 로그아웃")
	void logoutTest() {
		// given
		User mockUser = mock(User.class);

		// when
		userApplicationService.logout(mockUser);

		// then
		verify(mockUser, times(1)).deleteToken();
	}
}
