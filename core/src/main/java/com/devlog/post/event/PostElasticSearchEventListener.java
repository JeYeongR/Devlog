package com.devlog.post.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.devlog.post.domain.PostDocument;
import com.devlog.post.repository.PostSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostElasticSearchEventListener {

	private final PostSearchRepository postSearchRepository;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handlePostCreatedEvent(PostCreatedEvent event) {
		log.info("Post created event received: {}", event.post().getId());
		PostDocument document = PostDocument.from(event.post());
		postSearchRepository.save(document);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handlePostUpdatedEvent(PostUpdatedEvent event) {
		log.info("Post updated event received: {}", event.post().getId());
		PostDocument document = PostDocument.from(event.post());
		postSearchRepository.save(document);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handlePostDeletedEvent(PostDeletedEvent event) {
		log.info("Post deleted event received: {}", event.postId());
		postSearchRepository.deleteById(event.postId().toString());
	}
} 
