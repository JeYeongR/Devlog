package com.devlog.post.event;

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
import com.devlog.post.repository.PostSearchRepository;
import com.devlog.user.domain.User;

@ExtendWith(MockitoExtension.class)
class PostElasticSearchEventListenerTest {

	@Mock
	private PostSearchRepository postSearchRepository;

	@InjectMocks
	private PostElasticSearchEventListener eventListener;

	@Test
	@DisplayName("포스트 생성 이벤트 처리")
	void handlePostCreatedEventTest() {
		// given
		Post mockPost = mock(Post.class);
		when(mockPost.getId()).thenReturn(1L);
		when(mockPost.getTitle()).thenReturn("테스트 제목");
		when(mockPost.getContent()).thenReturn("테스트 내용");
		when(mockPost.getVisibilityStatus()).thenReturn(VisibilityStatus.PUBLIC);
		when(mockPost.getCreatedAt()).thenReturn(LocalDateTime.now());
		when(mockPost.getModifiedAt()).thenReturn(LocalDateTime.now());
		when(mockPost.getUser()).thenReturn(mock(User.class));
		PostCreatedEvent event = new PostCreatedEvent(mockPost);

		// when
		eventListener.handlePostCreatedEvent(event);

		// then
		verify(postSearchRepository, times(1)).save(any(PostDocument.class));
	}

	@Test
	@DisplayName("포스트 수정 이벤트 처리")
	void handlePostUpdatedEventTest() {
		// given
		Post mockPost = mock(Post.class);
		when(mockPost.getId()).thenReturn(1L);
		when(mockPost.getTitle()).thenReturn("테스트 제목");
		when(mockPost.getContent()).thenReturn("테스트 내용");
		when(mockPost.getVisibilityStatus()).thenReturn(VisibilityStatus.PUBLIC);
		when(mockPost.getCreatedAt()).thenReturn(LocalDateTime.now());
		when(mockPost.getModifiedAt()).thenReturn(LocalDateTime.now());
		when(mockPost.getUser()).thenReturn(mock(User.class));
		PostUpdatedEvent event = new PostUpdatedEvent(mockPost);

		// when
		eventListener.handlePostUpdatedEvent(event);

		// then
		verify(postSearchRepository, times(1)).save(any(PostDocument.class));
	}

	@Test
	@DisplayName("포스트 삭제 이벤트 처리")
	void handlePostDeletedEventTest() {
		// given
		Long mockPostId = 1L;
		PostDeletedEvent event = new PostDeletedEvent(mockPostId);

		// when
		eventListener.handlePostDeletedEvent(event);

		// then
		verify(postSearchRepository, times(1)).deleteById(mockPostId.toString());
	}
} 
