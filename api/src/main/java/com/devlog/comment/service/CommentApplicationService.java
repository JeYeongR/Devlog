package com.devlog.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlog.comment.domain.Comment;
import com.devlog.post.domain.Post;
import com.devlog.post.service.PostQueryService;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentApplicationService {

	private final CommentCommandService commentCommandService;
	private final PostQueryService postQueryService;

	@Transactional
	public void save(User user, Long postId, String content) {
		Post post = postQueryService.findPostById(postId);

		commentCommandService.save(Comment.create(content, user, post));
	}
}
