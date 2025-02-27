package com.devlog.follow.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.exception.ApiException;
import com.devlog.follow.domain.Follow;
import com.devlog.user.domain.User;
import com.devlog.user.service.UserQueryService;

@ExtendWith(MockitoExtension.class)
class FollowApplicationServiceTest {

	@Mock
	FollowCommandService followCommandService;

	@Mock
	FollowQueryService followQueryService;

	@Mock
	UserQueryService userQueryService;

	@InjectMocks
	FollowApplicationService followApplicationService;

	@Test
	@DisplayName("팔로우 성공(생성)")
	void followTest() {
		// given
		Long mockFollowedUserId = 1L;
		User mockFollower = mock(User.class);
		User mockFollowedUser = mock(User.class);

		when(userQueryService.findUserById(mockFollowedUserId)).thenReturn(mockFollowedUser);
		when(followQueryService.isFollowing(mockFollower, mockFollowedUser)).thenReturn(false);

		// when
		followApplicationService.follow(mockFollower, mockFollowedUserId);

		// then
		verify(userQueryService, times(1)).findUserById(mockFollowedUserId);
		verify(followQueryService, times(1)).isFollowing(mockFollower, mockFollowedUser);
		verify(followCommandService, times(1)).save(any(Follow.class));
	}

	@Test
	@DisplayName("같은 사용자 팔로우 시도")
	void followTestBadRequest() {
		// given
		Long mockFollowedUserId = 1L;
		User mockFollower = mock(User.class);

		when(userQueryService.findUserById(mockFollowedUserId)).thenReturn(mockFollower);

		// when
		assertThatThrownBy(() -> followApplicationService.follow(mockFollower, mockFollowedUserId))
			.isInstanceOf(ApiException.class);

		// then
		verify(userQueryService, times(1)).findUserById(mockFollowedUserId);
		verify(followQueryService, times(0)).isFollowing(mockFollower, mockFollower);
		verify(followCommandService, times(0)).save(any(Follow.class));
	}

	@Test
	@DisplayName("이미 있는 팔로우 시도")
	void followTestConflict() {
		// given
		Long mockFollowedUserId = 1L;
		User mockFollower = mock(User.class);
		User mockFollowedUser = mock(User.class);

		when(userQueryService.findUserById(mockFollowedUserId)).thenReturn(mockFollowedUser);
		when(followQueryService.isFollowing(mockFollower, mockFollowedUser)).thenReturn(true);

		// when
		assertThatThrownBy(() -> followApplicationService.follow(mockFollower, mockFollowedUserId))
			.isInstanceOf(ApiException.class);

		// then
		verify(userQueryService, times(1)).findUserById(mockFollowedUserId);
		verify(followQueryService, times(1)).isFollowing(mockFollower, mockFollowedUser);
		verify(followCommandService, times(0)).save(any(Follow.class));
	}
}
