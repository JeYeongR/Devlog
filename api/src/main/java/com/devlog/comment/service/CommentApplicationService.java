package com.devlog.comment.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlog.comment.domain.Comment;
import com.devlog.comment.response.CommentResponse;
import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.post.domain.Post;
import com.devlog.post.service.PostQueryService;
import com.devlog.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentApplicationService {

	private final CommentCommandService commentCommandService;
	private final CommentQueryService commentQueryService;
	private final PostQueryService postQueryService;

	@Transactional
	public void save(User user, Long postId, String content) {
		Post post = postQueryService.findPostById(postId);

		commentCommandService.save(Comment.create(content, user, post));
	}

	@Transactional(readOnly = true)
	public List<CommentResponse> findComments(Long postId, User user) {
		List<Comment> comments = commentQueryService.findComments(postId);

		return comments.stream()
			.map(comment -> CommentResponse.from(comment, user))
			.toList();
	}

	@Transactional
	public void update(User user, Long commentId, String content) {
		Comment comment = commentQueryService.findCommentById(commentId);

		if (!comment.getUser().equals(user)) {
			throw new ApiException("코멘트는 작성자만 수정할 수 있습니다.", ErrorType.FORBIDDEN, HttpStatus.FORBIDDEN);
		}

		comment.update(content);
	}
}
