package com.devlog.post.service;

import org.springframework.stereotype.Service;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostApplicationService {

	private final PostCommandService postCommandService;
	private final PostQueryService postQueryService;

	public Post save(
		String title,
		String content,
		VisibilityStatus visibilityStatus,
		User user
	) {
		return postCommandService.save(Post.create(
			title,
			content,
			visibilityStatus,
			user
		));
	}

	public Post findPost(Long postId) {
		return postQueryService.findPostById(postId);
	}
}
