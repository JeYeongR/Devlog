package com.devlog.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.domain.User;
import com.devlog.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserCommandService userCommandService;

	@Test
	@DisplayName("사용자 정상 저장")
	void saveTest() {
		// given
		User mockUser = mock(User.class);

		when(userRepository.save(mockUser)).thenReturn(mockUser);

		// when
		User result = userCommandService.save(mockUser);

		// then
		assertThat(result).isEqualTo(mockUser);
		verify(userRepository, times(1)).save(mockUser);
	}
}
