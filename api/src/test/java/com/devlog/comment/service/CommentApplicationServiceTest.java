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
import com.devlog.comment.response.CommentResponse;
import com.devlog.post.domain.Post;
import com.devlog.post.service.PostQueryService;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class CommentApplicationServiceTest {

	@Mock
	CommentCommandService commentCommandService;

	@Mock
	CommentQueryService commentQueryService;

	@Mock
	PostQueryService postQueryService;

	@InjectMocks
	CommentApplicationService commentApplicationService;

	@Test
	@DisplayName("코멘트 생성")
	void saveTest() {
		// given
		Long mockPostId = 1L;
		Comment mockComment = mock(Comment.class);

		when(postQueryService.findPostById(mockPostId)).thenReturn(mock(Post.class));
		when(commentCommandService.save(any(Comment.class))).thenReturn(mockComment);

		// when
		commentApplicationService.save(mock(User.class), mockPostId, "content");

		// then
		verify(postQueryService, times(1)).findPostById(mockPostId);
		verify(commentCommandService, times(1)).save(any(Comment.class));
	}

	@Test
	@DisplayName("코멘트 생성")
	void findCommentsTest() {
		// given
		Long mockPostId = 1L;
		List<Comment> mockComments = mock(List.class);
		User mockUser = mock(User.class);
		List<CommentResponse> mockCommentResponses = mockComments.stream()
			.map(comment -> CommentResponse.from(comment, mockUser))
			.toList();

		when(commentQueryService.findComments(mockPostId)).thenReturn(mockComments);

		// when
		List<CommentResponse> result = commentApplicationService.findComments(mockPostId, mockUser);

		// then
		assertThat(result).isEqualTo(mockCommentResponses);
		verify(commentQueryService, times(1)).findComments(mockPostId);
	}
}
