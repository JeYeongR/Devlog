package com.devlog.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.post.domain.Post;
import com.devlog.post.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostCommandServiceTest {

	@Mock
	PostRepository postRepository;

	@InjectMocks
	PostCommandService postCommandService;

	@Test
	@DisplayName("포스트 정상 저장")
	void saveTest() {
		// given
		Post mockPost = mock(Post.class);
		Post savedMockPost = mock(Post.class);
		when(postRepository.save(mockPost)).thenReturn(savedMockPost);

		// when
		Post result = postCommandService.save(mockPost);

		// then
		assertThat(result).isEqualTo(savedMockPost);
		verify(postRepository, times(1)).save(mockPost);
	}
}
