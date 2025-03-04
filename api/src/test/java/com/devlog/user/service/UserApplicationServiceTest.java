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
import com.devlog.user.response.UserDetailResponse;

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

	@Mock
	TokenQueryService tokenQueryService;

	@InjectMocks
	UserApplicationService userApplicationService;

	@Test
	@DisplayName("유저 단건 조회")
	void findUserTest() {
		// given
		User mockUser = mock(User.class);

		// when
		UserDetailResponse result = userApplicationService.findUser(mockUser);

		// then
		assertThat(result).isEqualTo(UserDetailResponse.from(mockUser));
	}

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
		verify(mockUser, times(1)).updateToken(mockToken);
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
		verify(mockUser, times(1)).updateToken(mockToken);
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

	@Test
	@DisplayName("토큰 재발급")
	void refreshTokenTest() {
		// given
		String mockAccessToken = "Bearer access";
		String mockRefreshToken = "Bearer refresh";
		String strippedAccessToken = mockAccessToken.substring("Bearer ".length());
		String strippedRefreshToken = mockRefreshToken.substring("Bearer ".length());

		Token mockToken = mock(Token.class);
		User mockUser = mock(User.class);
		when(mockToken.getUser()).thenReturn(mockUser);

		Token mockNewToken = mock(Token.class);

		when(tokenQueryService.findToken(strippedAccessToken, strippedRefreshToken)).thenReturn(mockToken);
		when(tokenIssueService.createTokens(mockUser.getId())).thenReturn(mockNewToken);

		// when
		TokenResponse result = userApplicationService.refreshToken(mockAccessToken, mockRefreshToken);

		// then
		assertThat(result).isEqualTo(TokenResponse.from(mockNewToken));
		verify(tokenQueryService, times(1)).findToken(strippedAccessToken, strippedRefreshToken);
		verify(mockToken, times(1)).getUser();
		verify(tokenIssueService, times(1)).createTokens(mockUser.getId());
		verify(mockUser, times(1)).updateToken(mockNewToken);
	}
}
