package com.devlog.comment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.comment.domain.Comment;
import com.devlog.comment.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
class CommentCommandServiceTest {

	@Mock
	CommentRepository commentRepository;

	@InjectMocks
	CommentCommandService commentCommandService;

	@Test
	@DisplayName("코멘트 정상 저장")
	void saveTest() {
		// given
		Comment mockComment = mock(Comment.class);

		when(commentRepository.save(mockComment)).thenReturn(mockComment);

		// when
		Comment result = commentCommandService.save(mockComment);

		// then
		assertThat(result).isEqualTo(mockComment);
		verify(commentRepository, times(1)).save(mockComment);
	}
}
