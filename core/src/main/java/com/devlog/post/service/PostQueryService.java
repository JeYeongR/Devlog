package com.devlog.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.post.domain.Post;
import com.devlog.post.domain.PostDocument;
import com.devlog.post.domain.VisibilityStatus;
import com.devlog.post.repository.PostRepository;
import com.devlog.post.repository.PostSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostQueryService {

	private final PostRepository postRepository;
	private final PostSearchRepository postSearchRepository;

	public Page<PostDocument> findPostsByQuery(String query, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		String publicStatus = VisibilityStatus.PUBLIC.name();

		return postSearchRepository.findByVisibilityStatusAndTitleContainingOrContentContaining(
			publicStatus,
			query,
			query,
			pageable);
	}

	public Page<PostDocument> findPosts(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		String publicStatus = VisibilityStatus.PUBLIC.name();

		List<PostDocument> postDocuments = postSearchRepository.findByVisibilityStatus(publicStatus, pageable);
		long totalPostDocument = postSearchRepository.countByVisibilityStatus(publicStatus);

		return new PageImpl<>(postDocuments, pageable, totalPostDocument);
	}

	public List<PostDocument> findPopularPosts() {
		return postSearchRepository.findTop10ByVisibilityStatusOrderByLikeCountDesc(VisibilityStatus.PUBLIC.name());
	}

	public Post findPostById(Long postId) {
		return postRepository.findByIdAndDeletedAtIsNull(postId)
			.orElseThrow(() -> new ApiException("포스트를 찾을 수 없습니다.", ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND));
	}
}
