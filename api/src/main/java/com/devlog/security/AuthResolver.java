package com.devlog.security;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;
import com.devlog.user.service.UserQueryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

	private final UserQueryService userQueryService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {

		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		Long userId = (Long)request.getAttribute("userId");

		LoginUser annotation = parameter.getParameterAnnotation(LoginUser.class);
		if (userId == null && annotation.required()) {
			throw new ApiException("인증이 필요합니다.", ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}

		return userId != null ? userQueryService.findUserById(userId) : null;
	}
}
