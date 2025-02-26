package com.devlog.comment.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devlog.comment.request.CommentCreateRequest;
import com.devlog.comment.request.CommentUpdateRequest;
import com.devlog.comment.service.CommentApplicationService;
import com.devlog.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
	controllers = CommentController.class,
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.REGEX,
		pattern = "com.devlog.security..*"
	)
)
class CommentControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	CommentApplicationService commentApplicationService;

	@Test
	@DisplayName("POST /v1/posts/{postId}/comments 코멘트 생성")
	void saveTest() throws Exception {
		// given
		Long mockPostId = 1L;
		CommentCreateRequest request = new CommentCreateRequest("Test Content");
		String requestJson = objectMapper.writeValueAsString(request);

		willDoNothing().given(commentApplicationService).save(any(User.class), anyLong(), anyString());

		// when || then
		mockMvc.perform(post("/v1/posts/{postId}/comments", mockPostId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /v1/posts/{postId}/comments 코멘트 조회")
	void findCommentsTest() throws Exception {
		// given
		Long mockPostId = 1L;

		given(commentApplicationService.findComments(mockPostId, mock(User.class)))
			.willReturn(mock(List.class));

		// when || then
		mockMvc.perform(get("/v1/posts/{postId}/comments", mockPostId))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("PATCH /v1/posts/{postId}/comments/{commentId} 코멘트 수정")
	void updateTest() throws Exception {
		// given
		Long mockCommentId = 1L;
		Long mockPostId = 1L;
		CommentUpdateRequest request = new CommentUpdateRequest("Test Content");
		String requestJson = objectMapper.writeValueAsString(request);

		willDoNothing().given(commentApplicationService).update(any(User.class), anyLong(), anyString());

		// when || then
		mockMvc.perform(patch("/v1/posts/{postId}/comments/{commentId}", mockPostId, mockCommentId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk());
	}
}
