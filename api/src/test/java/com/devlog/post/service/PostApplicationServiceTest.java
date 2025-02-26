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

import com.devlog.exception.ApiException;
import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.post.response.PagePostResponse;
import com.devlog.post.response.PostCreateResponse;
import com.devlog.post.response.PostDetailResponse;
import com.devlog.post.response.PostUpdateResponse;
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
		PostCreateResponse result = postApplicationService.save("title", "content", VisibilityStatus.PUBLIC,
			mock(User.class));

		// then
		assertThat(result).isEqualTo(PostCreateResponse.from(mockPost));
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
		PagePostResponse result = postApplicationService.search(mockQuery, mockPage, mockSize);

		// then
		assertThat(result).isEqualTo(PagePostResponse.from(mockPost));
		verify(postQueryService, times(1)).findPosts(mockQuery, mockPage, mockSize);
	}

	@Test
	@DisplayName("포스트 단건 정상 조회")
	void findPostTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		when(mockPost.getUser()).thenReturn(mockUser);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when
		PostDetailResponse result = postApplicationService.findPost(mockPostId, mockUser);

		// then
		assertThat(result).isEqualTo(PostDetailResponse.from(mockPost, mockUser));
		verify(postQueryService, times(1)).findPostById(mockPostId);
	}

	@Test
	@DisplayName("작성자가 아닌 비공개 포스트 단건 조회")
	void findPostTestPrivateForbidden() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		when(mockPost.getUser()).thenReturn(mock(User.class));
		when(mockPost.getVisibilityStatus()).thenReturn(VisibilityStatus.PRIVATE);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when | then
		assertThatThrownBy(() -> postApplicationService.findPost(mockPostId, mockUser))
			.isInstanceOf(ApiException.class);

		verify(postQueryService, times(1)).findPostById(mockPostId);
	}

	@Test
	@DisplayName("작성자가 아닌 작성중인 포스트 단건 조회")
	void findPostTestDraftForbidden() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		when(mockPost.getUser()).thenReturn(mock(User.class));
		when(mockPost.getVisibilityStatus()).thenReturn(VisibilityStatus.DRAFT);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when | then
		assertThatThrownBy(() -> postApplicationService.findPost(mockPostId, mockUser))
			.isInstanceOf(ApiException.class);

		verify(postQueryService, times(1)).findPostById(mockPostId);
	}

	@Test
	@DisplayName("포스트 정상 수정")
	void updateTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		when(mockPost.getUser()).thenReturn(mockUser);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when
		PostUpdateResponse result = postApplicationService.update(
			mockPostId,
			"title",
			"content",
			VisibilityStatus.PUBLIC,
			mockUser
		);

		// then
		assertThat(result).isEqualTo(PostUpdateResponse.from(mockPost));
		verify(postQueryService, times(1)).findPostById(mockPostId);
	}

	@Test
	@DisplayName("작성자가 아닌 포스트 수정")
	void updateTestForbidden() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		when(mockPost.getUser()).thenReturn(mock(User.class));

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when | then
		assertThatThrownBy(() -> postApplicationService.update(
			mockPostId,
			"title",
			"content",
			VisibilityStatus.PUBLIC,
			mockUser))
			.isInstanceOf(ApiException.class);

		verify(postQueryService, times(1)).findPostById(mockPostId);
	}

	@Test
	@DisplayName("포스트 정상 삭제")
	void deleteTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		when(mockPost.getUser()).thenReturn(mockUser);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when
		postApplicationService.delete(mockPostId, mockUser);

		// then
		verify(postQueryService, times(1)).findPostById(mockPostId);
	}

	@Test
	@DisplayName("작성자가 아닌 포스트 삭제")
	void deleteTestForbidden() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		when(mockPost.getUser()).thenReturn(mock(User.class));

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);

		// when | then
		assertThatThrownBy(() -> postApplicationService.delete(mockPostId, mockUser))
			.isInstanceOf(ApiException.class);

		verify(postQueryService, times(1)).findPostById(mockPostId);
	}
}
