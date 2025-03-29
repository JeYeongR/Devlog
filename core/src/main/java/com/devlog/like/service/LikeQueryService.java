package com.devlog.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devlog.like.domain.Like;
import com.devlog.like.repository.LikeRepository;
import com.devlog.post.domain.Post;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeQueryService {

	private final LikeRepository likeRepository;

	public Optional<Like> findLikeByUserAndPost(User user, Post post) {
		return likeRepository.findByUserAndPost(user, post);
	}

	public int findLikeCount(Post post) {
		return likeRepository.countByPost(post);
	}
}
