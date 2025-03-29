package com.devlog.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlog.post.domain.Post;
import com.devlog.post.domain.PostDocument;
import com.devlog.post.repository.PostRepository;
import com.devlog.post.repository.PostSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostCommandService {

	private final PostRepository postRepository;
	private final PostSearchRepository postSearchRepository;

	public Post save(Post post) {
		Post savedPost = postRepository.save(post);

		saveToElastic(savedPost);

		return savedPost;
	}

	public void deleteFromElastic(Long postId) {
		postSearchRepository.deleteById(postId.toString());
	}

	private void saveToElastic(Post post) {
		PostDocument document = PostDocument.from(post);
		postSearchRepository.save(document);
	}
}
