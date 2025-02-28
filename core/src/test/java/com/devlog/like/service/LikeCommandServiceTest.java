package com.devlog.like.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.like.domain.Like;
import com.devlog.like.repository.LikeRepository;

@ExtendWith(MockitoExtension.class)
class LikeCommandServiceTest {

	@Mock
	LikeRepository likeRepository;

	@InjectMocks
	LikeCommandService likeCommandService;

	@Test
	@DisplayName("라이크 정상 저장")
	void saveTest() {
		// given
		Like mockLike = mock(Like.class);

		when(likeRepository.save(mockLike)).thenReturn(mockLike);

		// when
		likeCommandService.save(mockLike);

		// then
		verify(likeRepository, times(1)).save(mockLike);
	}

	@Test
	@DisplayName("라이크 정상 삭제")
	void deleteTest() {
		// given
		Like mockLike = mock(Like.class);

		// when
		likeCommandService.delete(mockLike);

		// then
		verify(likeRepository, times(1)).delete(mockLike);
	}
}
