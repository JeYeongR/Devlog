package com.devlog.comment.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devlog.comment.domain.Comment;
import com.devlog.comment.repository.CommentRepository;
import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentQueryService {

	private final CommentRepository commentRepository;

	public List<Comment> findComments(Long postId) {
		return commentRepository.findAllByPostIdAndDeletedAtIsNullOrderByCreatedAtAsc(postId);
	}

	public Comment findCommentById(Long commentId) {
		return commentRepository.findByIdAndDeletedAtIsNull(commentId)
			.orElseThrow(() -> new ApiException("코멘트를 찾을 수 없습니다.", ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND));
	}
}
