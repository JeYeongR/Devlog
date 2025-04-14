package com.devlog.post.event;

import com.devlog.post.domain.Post;

public record PostUpdatedEvent(

	Post post
) {
}
