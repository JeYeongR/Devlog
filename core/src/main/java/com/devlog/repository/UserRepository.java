package com.devlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlog.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
