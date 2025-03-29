package com.devlog.like.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.like.domain.Like;
import com.devlog.like.repository.LikeRepository;
import com.devlog.post.domain.Post;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class LikeQueryServiceTest {

	@Mock
	LikeRepository likeRepository;

	@InjectMocks
	LikeQueryService likeQueryService;

	@Test
	@DisplayName("유저와 포스트로 라이크 정상 조회")
	void findFollowByFollowerAndFollowedUserTest() {
		// given
		User mockUser = mock(User.class);
		Post mockPost = mock(Post.class);
		Like mockLike = mock(Like.class);

		when(likeRepository.findByUserAndPost(mockUser, mockPost)).thenReturn(Optional.of(mockLike));

		// when
		Optional<Like> result = likeQueryService.findLikeByUserAndPost(mockUser, mockPost);

		// then
		assertThat(result).isPresent().contains(mockLike);
		verify(likeRepository, times(1)).findByUserAndPost(mockUser, mockPost);
	}

	@Test
	@DisplayName("포스트로 라이크 수 조회")
	void findLikeCountTest() {
		// given
		Post mockPost = mock(Post.class);
		int mockCount = 1;

		when(likeRepository.countByPost(mockPost)).thenReturn(mockCount);

		// when
		int result = likeQueryService.findLikeCount(mockPost);

		// then
		assertThat(result).isEqualTo(mockCount);
		verify(likeRepository, times(1)).countByPost(mockPost);
	}
}
