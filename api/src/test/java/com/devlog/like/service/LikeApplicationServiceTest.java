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
	void followTest() {
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
	void followTestConflict() {
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
}
