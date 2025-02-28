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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devlog.follow.request.FollowCreateRequest;
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
	@DisplayName("POST /v1/likes 라이크 생성")
	void followTest() throws Exception {
		// given
		Long mockFollowedUserId = 1L;
		FollowCreateRequest request = new FollowCreateRequest(mockFollowedUserId);
		String requestJson = objectMapper.writeValueAsString(request);

		willDoNothing().given(likeApplicationService).like(any(User.class), anyLong());

		// when || then
		mockMvc.perform(post("/v1/likes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk());
	}
}
