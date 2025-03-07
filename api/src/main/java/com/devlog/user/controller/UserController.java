package com.devlog.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.security.AuthRequired;
import com.devlog.security.LoginUser;
import com.devlog.user.domain.User;
import com.devlog.user.dto.response.TokenResponse;
import com.devlog.user.dto.response.UserDetailResponse;
import com.devlog.user.service.UserApplicationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	private final UserApplicationService userApplicationService;

	@AuthRequired
	@GetMapping
	public ResponseEntity<UserDetailResponse> findUser(@LoginUser User user) {
		UserDetailResponse response = userApplicationService.findUser(user);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/auth/callback")
	public ResponseEntity<TokenResponse> callback(@RequestParam String code) {
		TokenResponse response = userApplicationService.login(code);

		return ResponseEntity.ok(response);
	}

	@AuthRequired
	@PostMapping("/auth/logout")
	public ResponseEntity<Void> logout(@LoginUser User user) {
		userApplicationService.logout(user);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/auth/reissue")
	public ResponseEntity<TokenResponse> reissueToken(
		@RequestHeader("Authorization") String accessToken,
		@RequestHeader("Refresh-Token") String refreshToken
	) {
		TokenResponse response = userApplicationService.refreshToken(accessToken, refreshToken);

		return ResponseEntity.ok(response);
	}
}
