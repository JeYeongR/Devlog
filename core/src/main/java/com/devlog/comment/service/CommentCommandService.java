package com.devlog.comment.service;

import org.springframework.stereotype.Service;

import com.devlog.comment.domain.Comment;
import com.devlog.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentCommandService {

	private final CommentRepository commentRepository;

	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}
}
