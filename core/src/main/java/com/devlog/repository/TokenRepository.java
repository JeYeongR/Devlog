package com.devlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.domain.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
