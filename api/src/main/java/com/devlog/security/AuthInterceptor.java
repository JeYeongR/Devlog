package com.devlog.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.user.security.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String authorization = request.getHeader("Authorization");
		String token = getBearerToken(authorization);
		Long userId = jwtProvider.getUserIdFromToken(token);

		request.setAttribute("userId", userId);
		return true;
	}

	private String getBearerToken(String bearer) {
		if (bearer == null || !bearer.startsWith("Bearer ")) {
			throw new ApiException("Bearer 토큰이 아닙니다.", ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}

		return bearer.substring("Bearer ".length());
	}
}
