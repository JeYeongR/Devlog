package com.devlog.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.like.request.LikeCreateRequest;
import com.devlog.like.service.LikeApplicationService;
import com.devlog.security.AuthRequired;
import com.devlog.security.LoginUser;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/likes")
public class LikeController {

	private final LikeApplicationService likeApplicationService;

	@AuthRequired
	@PostMapping
	public ResponseEntity<Void> like(@LoginUser User user, @RequestBody LikeCreateRequest request) {
		likeApplicationService.like(user, request.postId());

		return ResponseEntity.ok().build();
	}

	@AuthRequired
	@DeleteMapping
	public ResponseEntity<Void> unlike(@LoginUser User user, @RequestBody LikeCreateRequest request) {
		likeApplicationService.unlike(user, request.postId());

		return ResponseEntity.ok().build();
	}
}
