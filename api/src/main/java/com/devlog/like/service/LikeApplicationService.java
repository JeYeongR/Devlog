package com.devlog.like.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.like.domain.Like;
import com.devlog.post.domain.Post;
import com.devlog.post.service.PostQueryService;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeApplicationService {

	private final LikeCommandService likeCommandService;
	private final LikeQueryService likeQueryService;
	private final PostQueryService postQueryService;

	public void like(User user, Long postId) {
		Post post = postQueryService.findPostById(postId);

		likeQueryService.findLikeByUserAndPost(user, post).ifPresent(like -> {
			throw new ApiException("이미 좋아요를 누른 게시물입니다.", ErrorType.CONFLICT, HttpStatus.CONFLICT);
		});

		likeCommandService.save(Like.create(user, post));
	}
}
