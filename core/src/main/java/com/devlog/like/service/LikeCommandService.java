package com.devlog.like.service;

import org.springframework.stereotype.Service;

import com.devlog.like.domain.Like;
import com.devlog.like.repository.LikeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeCommandService {

	private final LikeRepository likeRepository;

	public void save(Like like) {
		likeRepository.save(like);
	}

	public void delete(Like like) {
		likeRepository.delete(like);
	}
}
