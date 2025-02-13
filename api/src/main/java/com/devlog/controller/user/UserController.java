package com.devlog.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> callback(@RequestParam String code) {
		userApplicationService.login(code);
		return ResponseEntity.ok("hi");
	}
}

// 1359bb8d6b4cc975f069794e22b06d7e2d5ac099
