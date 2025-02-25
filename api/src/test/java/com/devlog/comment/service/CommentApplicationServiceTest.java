package com.devlog.comment.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devlog.comment.domain.Comment;
import com.devlog.post.domain.Post;
import com.devlog.post.service.PostQueryService;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class CommentApplicationServiceTest {

	@Mock
	CommentCommandService commentCommandService;

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
}
