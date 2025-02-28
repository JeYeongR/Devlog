package com.devlog.follow.service;

import org.springframework.stereotype.Service;

import com.devlog.follow.domain.Follow;
import com.devlog.follow.repository.FollowRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowCommandService {

	private final FollowRepository followRepository;

	public void save(Follow follow) {
		followRepository.save(follow);
	}

	public void delete(Follow follow) {
		followRepository.delete(follow);
	}
}
