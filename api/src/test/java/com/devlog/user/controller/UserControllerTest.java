package com.devlog.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devlog.user.domain.Token;
import com.devlog.user.domain.User;
import com.devlog.user.response.TokenResponse;
import com.devlog.user.service.UserApplicationService;

@WebMvcTest(
	controllers = UserController.class,
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.REGEX,
		pattern = "com.devlog.security..*"
	)
)
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	UserApplicationService userApplicationService;

	@Test
	@DisplayName("GET /v1/users/auth/callback 소셜로그인 콜백")
	void callbackTest() throws Exception {
		// given
		String code = "test-code";
		Token token = Token.create("test-access-token", "test-refresh-token");
		TokenResponse mockTokenResponse = TokenResponse.from(token);

		given(userApplicationService.login(code)).willReturn(mockTokenResponse);

		// when | then
		mockMvc.perform(get("/v1/users/auth/callback").param("code", code))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("POST /v1/users/auth/logout 로그아웃")
	void logoutTest() throws Exception {
		// given
		User mockUser = mock(User.class);

		willDoNothing().given(userApplicationService).logout(mockUser);

		// when | then
		mockMvc.perform(post("/v1/users/auth/logout"))
			.andExpect(status().isOk());
	}
}
