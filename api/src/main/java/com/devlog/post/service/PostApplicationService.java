package com.devlog.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.post.domain.Post;
import com.devlog.post.domain.PostDocument;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.post.dto.response.PagePostResponse;
import com.devlog.post.dto.response.PostCreateResponse;
import com.devlog.post.dto.response.PostDetailResponse;
import com.devlog.post.dto.response.PostResponse;
import com.devlog.post.dto.response.PostUpdateResponse;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostApplicationService {

	private final PostCommandService postCommandService;
	private final PostQueryService postQueryService;

	@Transactional
	public PostCreateResponse save(
		String title,
		String content,
		VisibilityStatus visibilityStatus,
		User user
	) {
		Post post = postCommandService.save(Post.create(
			title,
			content,
			visibilityStatus,
			user
		));

		return PostCreateResponse.from(post);
	}

	@Transactional(readOnly = true)
	public PagePostResponse search(String query, int page, int size) {
		Page<PostDocument> pageDocuments = postQueryService.findPosts(query, page, size);

		return PagePostResponse.fromDocumentPage(pageDocuments);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> findPopularPosts() {
		List<PostDocument> documents = postQueryService.findPopularPosts();

		return documents.stream()
			.map(PostResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PostDetailResponse findPost(Long postId, User user) {
		Post post = postQueryService.findPostById(postId);

		if (post.getVisibilityStatus() == VisibilityStatus.PRIVATE && !post.getUser().equals(user)) {
			throw new ApiException("비공개 포스트는 작성자만 조회할 수 있습니다.", ErrorType.FORBIDDEN, HttpStatus.FORBIDDEN);
		}

		if (post.getVisibilityStatus() == VisibilityStatus.DRAFT && !post.getUser().equals(user)) {
			throw new ApiException("작성중인 포스트는 작성자만 조회할 수 있습니다.", ErrorType.FORBIDDEN, HttpStatus.FORBIDDEN);
		}

		return PostDetailResponse.from(post, user);
	}

	@Transactional
	public PostUpdateResponse update(
		Long postId,
		String title,
		String content,
		VisibilityStatus visibilityStatus,
		User user
	) {
		Post post = postQueryService.findPostById(postId);

		if (!post.getUser().equals(user)) {
			throw new ApiException("작성자만 수정할 수 있습니다.", ErrorType.FORBIDDEN, HttpStatus.FORBIDDEN);
		}

		post.update(title, content, visibilityStatus);

		return PostUpdateResponse.from(post);
	}

	@Transactional
	public void delete(Long postId, User user) {
		Post post = postQueryService.findPostById(postId);

		if (!post.getUser().equals(user)) {
			throw new ApiException("작성자만 삭제할 수 있습니다.", ErrorType.FORBIDDEN, HttpStatus.FORBIDDEN);
		}

		post.delete();
		postCommandService.deleteFromElastic(postId);
	}
}
