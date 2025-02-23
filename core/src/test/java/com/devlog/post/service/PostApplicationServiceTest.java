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
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class PostApplicationServiceTest {

	@Mock
	PostCommandService postCommandService;

	@Mock
	PostQueryService postQueryService;

	@InjectMocks
	PostApplicationService postApplicationService;

	@Test
	@DisplayName("포스트 생성")
	void saveTest() {
		// given
		Post mockPost = mock(Post.class);

		when(postCommandService.save(any(Post.class))).thenReturn(mockPost);

		// when
		Post result = postApplicationService.save("title", "content", VisibilityStatus.PUBLIC, mock(User.class));

		// then
		assertThat(result).isEqualTo(mockPost);
		verify(postCommandService, times(1)).save(any(Post.class));
	}

	@Test
	@DisplayName("포스트 단건 조회")
	void findPostTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when
		Post result = postApplicationService.findPost(mockPostId);

		// then
		assertThat(result).isEqualTo(mockPost);
		verify(postQueryService, times(1)).findPostById(mockPostId);
	}
}
