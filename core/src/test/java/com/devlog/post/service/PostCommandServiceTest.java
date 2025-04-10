package com.devlog.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.PostDocument;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.post.repository.PostRepository;
import com.devlog.post.repository.PostSearchRepository;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class PostCommandServiceTest {

	@Mock
	PostRepository postRepository;

	@Mock
	PostSearchRepository postSearchRepository;

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

	@Test
	@DisplayName("포스트 정상 저장 - ElasticSearch에 저장")
	void saveToElasticTest() {
		// given
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);

		when(mockPost.getId()).thenReturn(1L);
		when(mockPost.getTitle()).thenReturn("테스트 제목");
		when(mockPost.getContent()).thenReturn("테스트 내용");
		when(mockPost.getVisibilityStatus()).thenReturn(VisibilityStatus.PUBLIC);
		when(mockUser.getId()).thenReturn(1L);
		when(mockUser.getNickname()).thenReturn("사용자");
		when(mockPost.getUser()).thenReturn(mockUser);
		when(mockPost.getLikeCount()).thenReturn(0L);
		when(mockPost.getCreatedAt()).thenReturn(LocalDateTime.parse("2023-01-01T00:00:00"));
		when(mockPost.getModifiedAt()).thenReturn(LocalDateTime.parse("2023-01-01T00:00:00"));

		// when
		postCommandService.saveToElastic(mockPost);

		// then
		verify(postSearchRepository, times(1)).save(any(PostDocument.class));
	}

	@Test
	@DisplayName("포스트 정상 삭제 - ElasticSearch에서 삭제")
	void deleteFromElasticTest() {
		// given
		Long mockPostId = 1L;

		// when
		postCommandService.deleteFromElastic(mockPostId);

		// then
		verify(postSearchRepository, times(1)).deleteById(mockPostId.toString());
	}
}
