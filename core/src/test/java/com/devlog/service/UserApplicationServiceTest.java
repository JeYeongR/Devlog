package com.devlog.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.domain.Token;
import com.devlog.domain.User;
import com.devlog.external.github.OauthUserResponse;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

	@Mock
	private UserCommandService userCommandService;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private AuthService authService;

	@Mock
	private TokenIssueService tokenIssueService;

	@InjectMocks
	private UserApplicationService userApplicationService;

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

		when(authService.getUserInfo(code)).thenReturn(response);
		when(userQueryService.findUser(response.socialProviderId())).thenReturn(Optional.empty());
		when(userCommandService.save(any(User.class))).thenReturn(mockUser);
		when(tokenIssueService.createTokens(mockUser.getId())).thenReturn(mockToken);

		// when
		Token result = userApplicationService.login(code);

		// then
		assertThat(result).isEqualTo(mockToken);
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

		when(authService.getUserInfo(code)).thenReturn(response);
		when(userQueryService.findUser(response.socialProviderId())).thenReturn(Optional.of(mockUser));
		when(tokenIssueService.createTokens(mockUser.getId())).thenReturn(mockToken);

		// when
		Token result = userApplicationService.login(code);

		// then
		assertThat(result).isEqualTo(mockToken);
		verify(authService, times(1)).getUserInfo(code);
		verify(userQueryService, times(1)).findUser(response.socialProviderId());
		verify(userCommandService, never()).save(any());
		verify(tokenIssueService, times(1)).createTokens(1L);
	}
}
