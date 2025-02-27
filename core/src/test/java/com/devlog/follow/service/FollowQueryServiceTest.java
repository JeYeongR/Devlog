package com.devlog.follow.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.follow.repository.FollowRepository;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class FollowQueryServiceTest {

	@Mock
	FollowRepository followRepository;

	@InjectMocks
	FollowQueryService followQueryService;

	@Test
	@DisplayName("팔로우가 없을 때 정상 조회")
	void isFollowingTestTrue() {
		// given
		User mockFollower = mock(User.class);
		User mockFollowedUser = mock(User.class);

		when(followRepository.existsByFollowerAndFollowedUser(mockFollower, mockFollowedUser)).thenReturn(true);

		// when
		boolean result = followQueryService.isFollowing(mockFollower, mockFollowedUser);

		// then
		assertThat(result).isTrue();
		verify(followRepository, times(1)).existsByFollowerAndFollowedUser(mockFollower, mockFollowedUser);
	}

	@Test
	@DisplayName("팔로우가 있을 때 정상 조회")
	void isFollowingTestFalse() {
		// given
		User mockFollower = mock(User.class);
		User mockFollowedUser = mock(User.class);

		when(followRepository.existsByFollowerAndFollowedUser(mockFollower, mockFollowedUser)).thenReturn(false);

		// when
		boolean result = followQueryService.isFollowing(mockFollower, mockFollowedUser);

		// then
		assertThat(result).isFalse();
		verify(followRepository, times(1)).existsByFollowerAndFollowedUser(mockFollower, mockFollowedUser);
	}
}
