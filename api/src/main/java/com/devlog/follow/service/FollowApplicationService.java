package com.devlog.follow.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.follow.domain.Follow;
import com.devlog.user.domain.User;
import com.devlog.user.service.UserQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowApplicationService {

	private final FollowCommandService followCommandService;
	private final FollowQueryService followQueryService;
	private final UserQueryService userQueryService;

	@Transactional
	public void follow(User follower, Long followedUserId) {
		User followedUser = userQueryService.findUserById(followedUserId);

		if (follower.equals(followedUser)) {
			throw new ApiException("자기 자신을 팔로우할 수 없습니다.", ErrorType.BAD_REQUEST, HttpStatus.BAD_REQUEST);
		}

		followQueryService.findFollowByFollowerAndFollowedUser(follower, followedUser)
			.ifPresent(follow -> {
				throw new ApiException("이미 팔로우 중입니다.", ErrorType.CONFLICT, HttpStatus.CONFLICT);
			});

		followCommandService.save(Follow.create(follower, followedUser));
	}

	@Transactional
	public void unfollow(User follower, Long followedUserId) {
		User followedUser = userQueryService.findUserById(followedUserId);

		if (follower.equals(followedUser)) {
			throw new ApiException("자기 자신을 언팔로우할 수 없습니다.", ErrorType.BAD_REQUEST, HttpStatus.BAD_REQUEST);
		}

		Follow follow = followQueryService.findFollowByFollowerAndFollowedUser(follower, followedUser)
			.orElseThrow(() -> new ApiException("팔로우 관계가 존재하지 않습니다.", ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND));

		followCommandService.delete(follow);
	}
}
