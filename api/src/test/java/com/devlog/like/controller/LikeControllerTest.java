package com.devlog.like.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devlog.like.dto.response.LikeCountResponse;
import com.devlog.like.service.LikeApplicationService;
import com.devlog.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
	controllers = LikeController.class,
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.REGEX,
		pattern = "com.devlog.security..*"
	)
)
class LikeControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	LikeApplicationService likeApplicationService;

	@Test
	@DisplayName("POST /v1/posts/{postId}/likes 라이크 생성")
	void likeTest() throws Exception {
		// given
		Long mockPostId = 1L;

		willDoNothing().given(likeApplicationService).like(any(User.class), anyLong());

		// when || then
		mockMvc.perform(post("/v1/posts/{postId}/likes", mockPostId))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("DELETE /v1/posts/{postId}/likes 라이크 삭제")
	void unlikeTest() throws Exception {
		// given
		Long mockPostId = 1L;

		willDoNothing().given(likeApplicationService).unlike(any(User.class), anyLong());

		// when || then
		mockMvc.perform(delete("/v1/posts/{postId}/likes", mockPostId))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /v1/posts/{postId}/likes 라이크 수 조회")
	void findLikeCountTest() throws Exception {
		// given
		Long mockPostId = 1L;

		given(likeApplicationService.findLikeCount(mockPostId)).willReturn(mock(LikeCountResponse.class));

		// when || then
		mockMvc.perform(get("/v1/posts/{postId}/likes", mockPostId))
			.andExpect(status().isOk());
	}
}
