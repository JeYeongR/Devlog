package com.devlog.post.service;

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
import com.devlog.post.domain.Post;
import com.devlog.post.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostQueryServiceTest {

	@Mock
	PostRepository postRepository;

	@InjectMocks
	PostQueryService postQueryService;

	@Test
	@DisplayName("포스트 아이디로 포스트 정상 조회")
	void findPostByIdTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);

		when(postRepository.findById(mockPostId)).thenReturn(Optional.of(mockPost));

		// when
		Post result = postQueryService.findPostById(mockPostId);

		// then
		assertThat(result).isEqualTo(mockPost);
		verify(postRepository, times(1)).findById(mockPostId);
	}

	@Test
	@DisplayName("포스트 아이디로 존재하지 않는 포스트 조회")
	void findPostByIdTestNotFound() {
		// given
		Long mockPostId = 0L;

		when(postRepository.findById(mockPostId)).thenReturn(Optional.empty());

		// when | then
		assertThatThrownBy(() -> postQueryService.findPostById(mockPostId))
			.isInstanceOf(ApiException.class);

		verify(postRepository, times(1)).findById(mockPostId);
	}
}
