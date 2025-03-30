package com.devlog.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.devlog.exception.ApiException;
import com.devlog.post.domain.Post;
import com.devlog.post.domain.PostDocument;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.post.repository.PostRepository;
import com.devlog.post.repository.PostSearchRepository;

@ExtendWith(MockitoExtension.class)
class PostQueryServiceTest {

	@Mock
	PostRepository postRepository;

	@Mock
	PostSearchRepository postSearchRepository;

	@InjectMocks
	PostQueryService postQueryService;

	@Test
	@DisplayName("포스트 쿼리로 검색")
	void findPostsTestWithQuery() {
		// given
		String mockQuery = "test";
		int mockPage = 1;
		int mockSize = 10;
		Pageable mockPageable = PageRequest.of(mockPage - 1, mockSize, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<PostDocument> mockPagePost = mock(Page.class);

		when(postSearchRepository.findByVisibilityStatusAndTitleContainingOrContentContaining(
			VisibilityStatus.PUBLIC.name(), mockQuery, mockQuery, mockPageable))
			.thenReturn(mockPagePost);

		// when
		Page<PostDocument> result = postQueryService.findPostsByQuery(mockQuery, mockPage, mockSize);

		// then
		assertThat(result).isEqualTo(mockPagePost);
		verify(postSearchRepository, times(1))
			.findByVisibilityStatusAndTitleContainingOrContentContaining(
				VisibilityStatus.PUBLIC.name(),
				mockQuery,
				mockQuery,
				mockPageable);
	}

	@Test
	@DisplayName("포스트 검색")
	void findPostsTest() {
		// given
		int mockPage = 1;
		int mockSize = 10;
		Pageable mockPageable = PageRequest.of(mockPage - 1, mockSize, Sort.by(Sort.Direction.DESC, "createdAt"));
		List<PostDocument> mockListPost = new ArrayList<>();
		Page<PostDocument> mockPagePost = new PageImpl<>(mockListPost, mockPageable, 0);

		when(postSearchRepository.findByVisibilityStatus(VisibilityStatus.PUBLIC.name(), mockPageable))
			.thenReturn(mockListPost);
		when(postSearchRepository.countByVisibilityStatus(VisibilityStatus.PUBLIC.name()))
			.thenReturn(0L);

		// when
		Page<PostDocument> result = postQueryService.findPosts(mockPage, mockSize);

		// then
		assertThat(result).isEqualTo(mockPagePost);
		verify(postSearchRepository, times(1))
			.findByVisibilityStatus(VisibilityStatus.PUBLIC.name(), mockPageable);
		verify(postSearchRepository, times(1))
			.countByVisibilityStatus(VisibilityStatus.PUBLIC.name());
	}

	@Test
	@DisplayName("인기 포스트 정상 조회")
	void findPopularPostsTest() {
		// given
		List<PostDocument> mockPosts = mock(List.class);

		when(postSearchRepository.findTop10ByVisibilityStatusOrderByLikeCountDesc(
			VisibilityStatus.PUBLIC.name()))
			.thenReturn(mockPosts);

		// when
		List<PostDocument> result = postQueryService.findPopularPosts();

		// then
		assertThat(result).isEqualTo(mockPosts);
		verify(postSearchRepository, times(1))
			.findTop10ByVisibilityStatusOrderByLikeCountDesc(VisibilityStatus.PUBLIC.name());
	}

	@Test
	@DisplayName("포스트 아이디로 포스트 단건 정상 조회")
	void findPostByIdTest() {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);

		when(postRepository.findByIdAndDeletedAtIsNull(mockPostId)).thenReturn(Optional.of(mockPost));

		// when
		Post result = postQueryService.findPostById(mockPostId);

		// then
		assertThat(result).isEqualTo(mockPost);
		verify(postRepository, times(1)).findByIdAndDeletedAtIsNull(mockPostId);
	}

	@Test
	@DisplayName("포스트 아이디로 존재하지 않는 포스트 조회")
	void findPostByIdTestNotFound() {
		// given
		Long mockPostId = 0L;

		when(postRepository.findByIdAndDeletedAtIsNull(mockPostId)).thenReturn(Optional.empty());

		// when | then
		assertThatThrownBy(() -> postQueryService.findPostById(mockPostId))
			.isInstanceOf(ApiException.class);

		verify(postRepository, times(1)).findByIdAndDeletedAtIsNull(mockPostId);
	}
}
