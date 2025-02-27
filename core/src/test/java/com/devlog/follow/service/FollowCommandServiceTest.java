package com.devlog.follow.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.follow.domain.Follow;
import com.devlog.follow.repository.FollowRepository;

@ExtendWith(MockitoExtension.class)
class FollowCommandServiceTest {

	@Mock
	FollowRepository followRepository;

	@InjectMocks
	FollowCommandService followCommandService;

	@Test
	@DisplayName("팔로우 정상 저장")
	void saveTest() {
		// given
		Follow mockFollow = mock(Follow.class);

		when(followRepository.save(mockFollow)).thenReturn(mockFollow);

		// when
		followCommandService.save(mockFollow);

		// then
		verify(followRepository, times(1)).save(mockFollow);
	}
}
