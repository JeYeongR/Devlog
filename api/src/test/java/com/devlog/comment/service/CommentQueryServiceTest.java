package com.devlog.comment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.comment.domain.Comment;
import com.devlog.comment.repository.CommentRepository;
import com.devlog.exception.ApiException;

@ExtendWith(MockitoExtension.class)
class CommentQueryServiceTest {

	@Mock
	CommentRepository commentRepository;

	@InjectMocks
	CommentQueryService commentQueryService;

	@Test
	@DisplayName("포스트 아이디로 코멘트 정상 조회")
	void findCommentsTest() {
		// given
		Long mockPostId = 1L;
		List<Comment> mockComments = mock(List.class);

		when(commentRepository.findAllByPostIdAndDeletedAtIsNullOrderByCreatedAtAsc(mockPostId))
			.thenReturn(mockComments);

		// when
		List<Comment> result = commentQueryService.findComments(mockPostId);

		// then
		assertThat(result).isEqualTo(mockComments);
		verify(commentRepository, times(1)).findAllByPostIdAndDeletedAtIsNullOrderByCreatedAtAsc(mockPostId);
	}

	@Test
	@DisplayName("코멘트 아이디로 코멘트 단건 정상 조회")
	void findCommentByIdTest() {
		// given
		Long mockPostId = 1L;
		Comment mockComment = mock(Comment.class);

		when(commentRepository.findByIdAndDeletedAtIsNull(mockPostId))
			.thenReturn(Optional.of(mockComment));

		// when
		Comment result = commentQueryService.findCommentById(mockPostId);

		// then
		assertThat(result).isEqualTo(mockComment);
		verify(commentRepository, times(1)).findByIdAndDeletedAtIsNull(mockPostId);
	}

	@Test
	@DisplayName("코멘트 아이디로 존재하지 않는 코멘트 단건 조회")
	void findCommentByIdTestNotFound() {
		// given
		Long mockPostId = 0L;

		when(commentRepository.findByIdAndDeletedAtIsNull(mockPostId)).thenReturn(Optional.empty());

		// when | then
		assertThatThrownBy(() -> commentQueryService.findCommentById(mockPostId))
			.isInstanceOf(ApiException.class);

		verify(commentRepository, times(1)).findByIdAndDeletedAtIsNull(mockPostId);
	}
}
