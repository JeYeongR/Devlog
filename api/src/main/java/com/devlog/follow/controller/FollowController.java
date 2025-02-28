package com.devlog.follow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.follow.request.FollowCreateRequest;
import com.devlog.follow.request.FollowDeleteRequest;
import com.devlog.follow.service.FollowApplicationService;
import com.devlog.security.AuthRequired;
import com.devlog.security.LoginUser;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/follows")
public class FollowController {

	private final FollowApplicationService followApplicationService;

	@AuthRequired
	@PostMapping
	public ResponseEntity<Void> follow(@LoginUser User user, @RequestBody FollowCreateRequest request) {
		followApplicationService.follow(user, request.followedUserId());

		return ResponseEntity.ok().build();
	}

	@AuthRequired
	@DeleteMapping
	public ResponseEntity<Void> unfollow(@LoginUser User user, @RequestBody FollowDeleteRequest request) {
		followApplicationService.unfollow(user, request.followedUserId());

		return ResponseEntity.ok().build();
	}
}
