package com.devlog.comment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.comment.domain.Comment;
import com.devlog.comment.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
class CommentQueryServiceTest {

	@Mock
	CommentRepository commentRepository;

	@InjectMocks
	CommentQueryService commentQueryService;

	@Test
	@DisplayName("코멘트 정상 조회")
	void findCommentsTest() {
		// given
		Long mockPostId = 1L;
		List<Comment> mockPosts = mock(List.class);

		when(commentRepository.findAllByPostIdOrderByCreatedAtAsc(mockPostId))
			.thenReturn(mockPosts);

		// when
		List<Comment> result = commentQueryService.findComments(mockPostId);

		// then
		assertThat(result).isEqualTo(mockPosts);
		verify(commentRepository, times(1)).findAllByPostIdOrderByCreatedAtAsc(mockPostId);
	}
}
