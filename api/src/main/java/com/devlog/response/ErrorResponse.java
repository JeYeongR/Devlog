package com.devlog.response;

import com.devlog.exception.ErrorType;

public record ErrorResponse(

	String errorMessage,

	ErrorType errorType
) {
}
