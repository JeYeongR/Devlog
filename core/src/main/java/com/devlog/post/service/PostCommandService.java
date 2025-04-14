package com.devlog.post.service;

import org.springframework.stereotype.Service;

import com.devlog.post.domain.Post;
import com.devlog.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCommandService {

	private final PostRepository postRepository;

	public Post save(Post post) {
		return postRepository.save(post);
	}
}
