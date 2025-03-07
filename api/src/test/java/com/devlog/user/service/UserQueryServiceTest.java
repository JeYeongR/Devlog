package com.devlog.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.exception.ApiException;
import com.devlog.user.domain.User;
import com.devlog.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserQueryService userQueryService;

	@Test
	@DisplayName("소설 제공 아이디로 사용자 정상 조회")
	void findUserTest() {
		// given
		String testProviderId = "test-12345";
		User mockUser = mock(User.class);

		when(mockUser.getSocialProviderId()).thenReturn(testProviderId);

		when(userRepository.findBySocialProviderId(testProviderId)).thenReturn(Optional.of(mockUser));

		// when
		Optional<User> result = userQueryService.findUser(testProviderId);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getSocialProviderId()).isEqualTo(testProviderId);
		verify(userRepository, times(1)).findBySocialProviderId(testProviderId);
	}

	@Test
	@DisplayName("소설 제공 아이디로 존재하지 않는 사용자 조회")
	void findUserTestNotFound() {
		// given
		String invalidProviderId = "invalid-123";

		when(userRepository.findBySocialProviderId(invalidProviderId)).thenReturn(Optional.empty());

		// when
		Optional<User> result = userQueryService.findUser(invalidProviderId);

		// then
		assertThat(result).isEmpty();
		verify(userRepository, times(1)).findBySocialProviderId(invalidProviderId);
	}

	@Test
	@DisplayName("유저 아이디로 사용자 정상 조회")
	void findUserByIdTest() {
		// given
		Long userId = 0L;
		User user = mock(User.class);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// when
		User result = userQueryService.findUserById(userId);

		// then
		assertThat(result).isEqualTo(user);
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	@DisplayName("유저 아이디로 존재하지 않는 사용자 조회")
	void findUserByIdTestNotFound() {
		// given
		Long userId = 0L;

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// when | then
		assertThatThrownBy(() -> userQueryService.findUserById(userId))
			.isInstanceOf(ApiException.class);

		verify(userRepository, times(1)).findById(userId);
	}
}
