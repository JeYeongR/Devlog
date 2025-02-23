package com.devlog.post.service;

import org.springframework.stereotype.Service;

import com.devlog.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostQueryService {

	private final PostRepository postRepository;
}
