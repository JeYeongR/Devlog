package com.devlog.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.like.dto.response.LikeCountResponse;
import com.devlog.like.service.LikeApplicationService;
import com.devlog.security.AuthRequired;
import com.devlog.security.LoginUser;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts/{postId}/likes")
public class LikeController {

	private final LikeApplicationService likeApplicationService;

	@AuthRequired
	@PostMapping
	public ResponseEntity<Void> like(@LoginUser User user, @PathVariable Long postId) {
		likeApplicationService.like(user, postId);

		return ResponseEntity.ok().build();
	}

	@AuthRequired
	@DeleteMapping
	public ResponseEntity<Void> unlike(@LoginUser User user, @PathVariable Long postId) {
		likeApplicationService.unlike(user, postId);

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<LikeCountResponse> findLikeCount(@PathVariable Long postId) {
		LikeCountResponse response = likeApplicationService.findLikeCount(postId);

		return ResponseEntity.ok(response);
	}
}
