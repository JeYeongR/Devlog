package com.devlog.follow.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.follow.domain.Follow;
import com.devlog.follow.repository.FollowRepository;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class FollowQueryServiceTest {

	@Mock
	FollowRepository followRepository;

	@InjectMocks
	FollowQueryService followQueryService;

	@Test
	@DisplayName("팔로워와 팔로우 할 유저로 팔로우 정상 조회")
	void findFollowByFollowerAndFollowedUserTest() {
		// given
		User mockFollower = mock(User.class);
		User mockFollowedUser = mock(User.class);
		Follow mockFollow = mock(Follow.class);

		when(followRepository.findByFollowerAndFollowedUser(mockFollower, mockFollowedUser))
			.thenReturn(Optional.of(mockFollow));

		// when
		Optional<Follow> result =
			followQueryService.findFollowByFollowerAndFollowedUser(mockFollower, mockFollowedUser);

		// then
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(mockFollow);
		verify(followRepository, times(1)).findByFollowerAndFollowedUser(mockFollower, mockFollowedUser);
	}
}
