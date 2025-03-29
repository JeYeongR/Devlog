package com.devlog.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.user.domain.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Optional<Token> findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);
}
