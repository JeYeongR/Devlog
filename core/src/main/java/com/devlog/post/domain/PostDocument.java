package com.devlog.post.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Document(indexName = "posts")
public class PostDocument {

	@Id
	private String id;

	@Field(type = FieldType.Long)
	private Long postId;

	@Field(type = FieldType.Text, analyzer = "standard")
	private String title;

	@Field(type = FieldType.Text, analyzer = "standard")
	private String content;

	@Field(type = FieldType.Keyword)
	private String visibilityStatus;

	@Field(type = FieldType.Long)
	private Long userId;

	@Field(type = FieldType.Text)
	private String userName;

	@Field(type = FieldType.Long)
	private long likeCount;

	@Field(type = FieldType.Date)
	private String createdAt;

	@Field(type = FieldType.Date)
	private String modifiedAt;

	public static PostDocument from(Post post) {
		return new PostDocument(
			post.getId().toString(),
			post.getId(),
			post.getTitle(),
			post.getContent(),
			post.getVisibilityStatus().name(),
			post.getUser().getId(),
			post.getUser().getNickname(),
			post.getLikeCount(),
			post.getCreatedAt().toString(),
			post.getModifiedAt().toString());
	}
}
