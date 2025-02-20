package com.devlog.exception;

public record ErrorResponse(

	String errorMessage,

	ErrorType errorType
) {
}
