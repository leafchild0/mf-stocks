package com.mf.appshell.security;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

/**
 * Provider for JWT tokens
 * Manages, generate and validate tokens
 */
@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${auth-service.jwtSecret}")
	private String jwtSecret;

	public Long getUserIdFromJWT(Claims claims) {

		return Long.parseLong(claims.getSubject());
	}

	public Claims getClaims(String token) {

		return Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody();

	}

	private SecretKey getKey() {

		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	public boolean validateToken(String authToken) {

		try {
			Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(authToken);

			return true;
		}
		catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		}
		catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		}
		catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		}
		catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
