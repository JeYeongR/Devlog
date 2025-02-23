package com.devlog.post.service;

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

import com.devlog.external.github.OauthUserResponse;
import com.devlog.post.domain.Post;
import com.devlog.post.repository.PostRepository;
import com.devlog.user.domain.Token;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class PostCommandServiceTest {

	@Mock
	PostRepository postRepository;

	@InjectMocks
	PostCommandService postCommandService;

	@Test
	@DisplayName("신규 사용자 로그인")
	void loginTest() {
		// given
		Post mockPost = mock(Post.class);

		when(postRepository.save(mockPost)).thenReturn(mockPost);

		// when
		Post result = postCommandService.save(mockPost);

		// then
		assertThat(result).isEqualTo(mockPost);
		verify(postRepository, times(1)).save(mockPost);
	}
}
