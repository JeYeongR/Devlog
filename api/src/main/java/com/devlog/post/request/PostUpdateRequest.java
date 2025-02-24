package com.devlog.post.request;

import com.devlog.post.domain.VisibilityStatus;

public record PostUpdateRequest(

	String title,

	String content,

	VisibilityStatus visibilityStatus
) {
}
