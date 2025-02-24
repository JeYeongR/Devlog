package com.devlog.post.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.post.request.PostCreateRequest;
import com.devlog.post.request.PostSearchRequest;
import com.devlog.post.response.PagePostResult;
import com.devlog.post.response.PostCreateResponse;
import com.devlog.post.response.PostDetailResponse;
import com.devlog.post.service.PostApplicationService;
import com.devlog.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
	controllers = PostController.class,
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.REGEX,
		pattern = "com.devlog.security..*"
	)
)
class PostControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	PostApplicationService postApplicationService;

	@Test
	@DisplayName("GET /v1/posts 포스트 생성")
	void saveTest() throws Exception {
		// given
		PostCreateRequest request = new PostCreateRequest("Test Title", "Test Content", VisibilityStatus.PUBLIC);
		String requestJson = objectMapper.writeValueAsString(request);

		// when
		given(postApplicationService.save(
			any(String.class),
			any(String.class),
			any(VisibilityStatus.class),
			any(User.class)))
			.willReturn(mock(PostCreateResponse.class));

		// then
		mockMvc.perform(post("/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /v1/posts/search 포스트 검색 조회")
	void searchTest() throws Exception {
		// given
		PostSearchRequest mockRequest = new PostSearchRequest("test", 1, 10);

		// when
		given(postApplicationService.search(mockRequest.query(), mockRequest.page(), mockRequest.size()))
			.willReturn(mock(PagePostResult.class));

		// then
		mockMvc.perform(get("/v1/posts/search")
				.param("query", mockRequest.query())
				.param("page", String.valueOf(mockRequest.page()))
				.param("size", String.valueOf(mockRequest.size())))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /v1/posts/{postId} 포스트 단건 조회")
	void findPostTest() throws Exception {
		// given
		Long mockPostId = 1L;
		Post mockPost = mock(Post.class);
		User mockUser = mock(User.class);

		when(mockPost.getUser()).thenReturn(mockUser);

		// when
		given(postApplicationService.findPost(mockPostId, mockUser))
			.willReturn(mock(PostDetailResponse.class));

		// then
		mockMvc.perform(get("/v1/posts/{postId}", mockPostId.toString()))
			.andExpect(status().isOk());
	}
}
