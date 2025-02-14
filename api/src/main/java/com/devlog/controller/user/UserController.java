package com.devlog.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.response.TokenResponse;
import com.devlog.service.UserApplicationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	private final UserApplicationService userApplicationService;

	@GetMapping("/auth/callback")
	public ResponseEntity<TokenResponse> callback(@RequestParam String code) {
		TokenResponse response = TokenResponse.from(userApplicationService.login(code));

		return ResponseEntity.ok(response);
	}
}
