package com.devlog.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.user.response.TokenResponse;
import com.devlog.user.service.UserApplicationService;

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
		TokenResponse response = userApplicationService.login(code);

		return ResponseEntity.ok(response);
	}
}
