package com.devlog.post.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.devlog.post.domain.PostDocument;

public record PagePostResponse(

	int page,

	int size,

	long totalElements,

	List<PostResponse> contents
) {

	public static PagePostResponse fromDocumentPage(Page<PostDocument> pageDocuments) {
		return new PagePostResponse(
			pageDocuments.getNumber(),
			pageDocuments.getSize(),
			pageDocuments.getTotalElements(),
			pageDocuments.getContent().stream()
				.map(PostResponse::from)
				.toList()
		);
	}
}
