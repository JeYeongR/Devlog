package com.devlog.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.comment.request.CommentCreateRequest;
import com.devlog.comment.service.CommentApplicationService;
import com.devlog.security.AuthRequired;
import com.devlog.security.LoginUser;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts/{postId}/comments")
public class CommentController {

	private final CommentApplicationService commentApplicationService;

	@AuthRequired
	@PostMapping
	public ResponseEntity<Void> save(
		@LoginUser User user,
		@PathVariable Long postId,
		@RequestBody CommentCreateRequest request
	) {
		commentApplicationService.save(user, postId, request.content());

		return ResponseEntity.ok().build();
	}
}
