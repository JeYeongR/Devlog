package com.devlog.post.dto.request;

import com.devlog.post.domain.VisibilityStatus;

public record PostUpdateRequest(

	String title,

	String content,

	VisibilityStatus visibilityStatus
) {
}
