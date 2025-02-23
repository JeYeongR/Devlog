package com.devlog.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {
		boolean isAuthRequired = isAuthRequired(handler);
		String authorization = request.getHeader("Authorization");
		Long userId = null;

		try {
			if (authorization != null) {
				String token = getBearerToken(authorization);
				userId = jwtProvider.getUserIdFromToken(token);
			} else if (isAuthRequired) {
				throw new ApiException("Authorization 헤더 필요", ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
			}
		} catch (ApiException e) {
			if (isAuthRequired)
				throw e;
		} catch (Exception e) {
			if (isAuthRequired) {
				throw new ApiException("토큰 검증 실패", ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
			}
		}

		request.setAttribute("userId", userId);
		return true;
	}

	private String getBearerToken(String bearer) {
		if (bearer == null || !bearer.startsWith("Bearer ")) {
			throw new ApiException("Bearer 토큰이 아닙니다.", ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}

		return bearer.substring("Bearer ".length());
	}

	private boolean isAuthRequired(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			return handlerMethod.getMethodAnnotation(AuthRequired.class) != null
				|| handlerMethod.getBeanType().getAnnotation(AuthRequired.class) != null;
		}
		return false;
	}
}
