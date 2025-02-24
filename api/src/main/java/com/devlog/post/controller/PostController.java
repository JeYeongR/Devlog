package com.devlog.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlog.post.request.PostCreateRequest;
import com.devlog.post.request.PostSearchRequest;
import com.devlog.post.request.PostUpdateRequest;
import com.devlog.post.response.PagePostResult;
import com.devlog.post.response.PostCreateResponse;
import com.devlog.post.response.PostDetailResponse;
import com.devlog.post.response.PostUpdateResponse;
import com.devlog.post.service.PostApplicationService;
import com.devlog.security.AuthRequired;
import com.devlog.security.LoginUser;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

	private final PostApplicationService postApplicationService;

	@AuthRequired
	@PostMapping
	public ResponseEntity<PostCreateResponse> save(@LoginUser User user, @RequestBody PostCreateRequest request) {
		PostCreateResponse response = postApplicationService.save(
			request.title(),
			request.content(),
			request.visibilityStatus(),
			user
		);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<PagePostResult> search(PostSearchRequest request) {
		PagePostResult result = postApplicationService.search(request.query(), request.page(), request.size());

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDetailResponse> findPost(
		@LoginUser(required = false) User user,
		@PathVariable Long postId
	) {
		PostDetailResponse response = postApplicationService.findPost(postId, user);

		return ResponseEntity.ok(response);
	}

	@AuthRequired
	@PatchMapping("/{postId}")
	public ResponseEntity<PostUpdateResponse> update(
		@LoginUser User user,
		@PathVariable Long postId,
		@RequestBody PostUpdateRequest request
	) {
		PostUpdateResponse response = postApplicationService.update(
			postId,
			request.title(),
			request.content(),
			request.visibilityStatus(),
			user
		);

		return ResponseEntity.ok(response);
	}
}
