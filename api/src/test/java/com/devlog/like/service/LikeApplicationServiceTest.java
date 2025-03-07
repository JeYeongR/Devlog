package com.devlog.like.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.exception.ApiException;
import com.devlog.like.domain.Like;
import com.devlog.like.dto.response.LikeCountResponse;
import com.devlog.post.domain.Post;
import com.devlog.post.service.PostQueryService;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class LikeApplicationServiceTest {

	@Mock
	LikeCommandService likeCommandService;

	@Mock
	LikeQueryService likeQueryService;

	@Mock
	PostQueryService postQueryService;

	@InjectMocks
	LikeApplicationService likeApplicationService;

	@Test
	@DisplayName("라이크 성공(생성)")
	void likeTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);
		when(likeQueryService.findLikeByUserAndPost(mockUser, mockPost)).thenReturn(Optional.empty());

		// when
		likeApplicationService.like(mockUser, mockPostId);

		// then
		verify(postQueryService, times(1)).findPostById(mockPostId);
		verify(likeQueryService, times(1)).findLikeByUserAndPost(mockUser, mockPost);
		verify(likeCommandService, times(1)).save(any(Like.class));
	}

	@Test
	@DisplayName("이미 있는 라이크 시도")
	void likeTestConflict() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);
		when(likeQueryService.findLikeByUserAndPost(mockUser, mockPost)).thenReturn(Optional.of(mock(Like.class)));

		// when || then
		assertThatThrownBy(() -> likeApplicationService.like(mockUser, mockPostId)).isInstanceOf(ApiException.class);

		verify(postQueryService, times(1)).findPostById(mockPostId);
		verify(likeQueryService, times(1)).findLikeByUserAndPost(mockUser, mockPost);
		verify(likeCommandService, times(0)).save(any(Like.class));
	}

	@Test
	@DisplayName("언라이크 성공(삭제)")
	void unlikeTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);
		Like mockLike = mock(Like.class);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);
		when(likeQueryService.findLikeByUserAndPost(mockUser, mockPost)).thenReturn(Optional.of(mockLike));

		// when
		likeApplicationService.unlike(mockUser, mockPostId);

		// then
		verify(postQueryService, times(1)).findPostById(mockPostId);
		verify(likeQueryService, times(1)).findLikeByUserAndPost(mockUser, mockPost);
		verify(likeCommandService, times(1)).delete(mockLike);
	}

	@Test
	@DisplayName("존재하지 않는 언라이크 시도")
	void unlikeTestNotFound() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);
		when(likeQueryService.findLikeByUserAndPost(mockUser, mockPost)).thenReturn(Optional.empty());

		// when || then
		assertThatThrownBy(() -> likeApplicationService.unlike(mockUser, mockPostId)).isInstanceOf(ApiException.class);

		verify(postQueryService, times(1)).findPostById(mockPostId);
		verify(likeQueryService, times(1)).findLikeByUserAndPost(mockUser, mockPost);
		verify(likeCommandService, times(0)).delete(any(Like.class));
	}

	@Test
	@DisplayName("라이크 수 정상 조회")
	void findLikeCountTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		int mockCount = 1;

		when(postQueryService.findPostById(mockPostId)).thenReturn(mockPost);
		when(likeQueryService.findLikeCount(mockPost)).thenReturn(mockCount);

		// when
		LikeCountResponse result = likeApplicationService.findLikeCount(mockPostId);

		// then
		assertThat(result).isEqualTo(LikeCountResponse.from(mockCount));
		verify(postQueryService, times(1)).findPostById(mockPostId);
		verify(likeQueryService, times(1)).findLikeCount(mockPost);
	}
}
