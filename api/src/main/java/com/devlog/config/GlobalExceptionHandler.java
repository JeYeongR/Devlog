package com.devlog.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorResponse;
import com.devlog.exception.ErrorType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
		log.error("Api Exception occurred. message={}, className={}", e.getErrorMessage(), e.getClass().getName());
		return ResponseEntity.status(e.getHttpStatus())
			.body(new ErrorResponse(e.getErrorMessage(), e.getErrorType()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("Exception occurred. message={}, className={}", e.getMessage(), e.getClass().getName());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ErrorResponse(ErrorType.UNKNOWN.getDescription(), ErrorType.UNKNOWN));
	}
}
