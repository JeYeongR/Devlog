package com.devlog.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.devlog.exception.ApiException;
import com.devlog.exception.ErrorType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	@Value("${jwt.secret-key}")
	private String secretKeyString;

	@Value("${jwt.access-token-expiration-ms}")
	private long accessTokenExpirationMs;

	@Value("${jwt.refresh-token-expiration-ms}")
	private long refreshTokenExpirationMs;

	public String createAccessToken(Long userId) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + accessTokenExpirationMs);

		return Jwts.builder()
			.subject(String.valueOf(userId))
			.issuedAt(now)
			.expiration(expiration)
			.signWith(getSecretKey())
			.compact();
	}

	public String createRefreshToken(Long userId) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + refreshTokenExpirationMs);

		return Jwts.builder()
			.subject(String.valueOf(userId))
			.issuedAt(now)
			.expiration(expiration)
			.signWith(getSecretKey())
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		try {
			Claims claims = parseClaims(token);

			return Long.parseLong(claims.getSubject());
		} catch (ExpiredJwtException e) {
			throw new ApiException("토큰이 만료되었습니다.", ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
	}

	public Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(getSecretKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
	}
}
