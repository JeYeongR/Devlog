package com.devlog.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.devlog.post.domain.PostDocument;

public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, String> {

	Page<PostDocument> findByVisibilityStatusAndTitleContainingOrContentContaining(
		String visibilityStatus,
		String titleQuery,
		String contentQuery,
		Pageable pageable);

	Page<PostDocument> findByVisibilityStatus(String visibilityStatus, Pageable pageable);

	List<PostDocument> findTop10ByVisibilityStatusOrderByLikeCountDesc(String visibilityStatus);
}
