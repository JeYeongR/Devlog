package com.devlog.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devlog.comment.domain.Comment;
import com.devlog.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentQueryService {

	private final CommentRepository commentRepository;

	public List<Comment> findComments(Long postId) {
		return commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);
	}
}
