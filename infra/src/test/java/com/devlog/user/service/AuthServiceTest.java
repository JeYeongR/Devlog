package com.devlog.user.service;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.external.github.AuthService;
import com.devlog.external.github.GithubClient;
import com.devlog.external.github.OauthUserResponse;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	GithubClient githubClient;

	@InjectMocks
	AuthService authService;

	@Test
	@DisplayName("깃허브 유저 정상 조회")
	void getUserInfoTest() {
		//given
		String code = "test-code";

		OauthUserResponse mockResponse = mock(OauthUserResponse.class);

		when(githubClient.getUserInfo(code)).thenReturn(mockResponse);

		//when
		OauthUserResponse result = authService.getUserInfo(code);

		//then
		Assertions.assertThat(result).isEqualTo(mockResponse);
		verify(githubClient, times(1)).getUserInfo(code);
	}
}
