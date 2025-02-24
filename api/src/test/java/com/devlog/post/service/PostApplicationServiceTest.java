package com.devlog.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.post.response.PagePostResult;
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
	@DisplayName("포스트 검색 조회")
	void searchTest() {
		// given
		String mockQuery = "test";
		int mockPage = 1;
		int mockSize = 10;
		Page<Post> mockPost = mock(Page.class);

		when(postQueryService.findPosts(mockQuery, mockPage, mockSize)).thenReturn(mockPost);

		// when
		PagePostResult result = postApplicationService.search(mockQuery, mockPage, mockSize);

		// then
		assertThat(result).isEqualTo(PagePostResult.from(mockPost));
		verify(postQueryService, times(1)).findPosts(mockQuery, mockPage, mockSize);
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
