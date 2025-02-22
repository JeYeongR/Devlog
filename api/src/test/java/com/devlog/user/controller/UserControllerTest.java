package com.devlog.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devlog.user.domain.Token;
import com.devlog.security.JwtProvider;
import com.devlog.user.service.UserApplicationService;
import com.devlog.user.service.UserQueryService;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	JwtProvider jwtProvider;

	@MockitoBean
	UserQueryService userQueryService;

	@MockitoBean
	UserApplicationService userApplicationService;

	@Test
	@DisplayName("GET /v1/users/auth/callback 소셜로그인 콜백")
	void callbackTest() throws Exception {
		// given
		String code = "test-code";
		Token token = Token.create("test-access-token", "test-refresh-token");

		given(userApplicationService.login(code)).willReturn(token);

		mockMvc.perform(get("/v1/users/auth/callback").param("code", code))
			.andExpect(status().isOk());
	}
}
