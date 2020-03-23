package com.poc.authserver.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.poc.authserver.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider
{
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${auth-service.jwtSecret}")
	private String jwtSecret;

	@Value("${auth-service.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication)
	{
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Instant now = Instant.now();
		return Jwts.builder()
			.setSubject(Long.toString(userPrincipal.getId()))
			.claim("authorities", userPrincipal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plusSeconds(jwtExpirationInMs)))
			.signWith(getKey())
			.compact();
	}

	public Long getUserIdFromJWT(String token)
	{
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody();

		return Long.parseLong(claims.getSubject());
	}

	private SecretKey getKey()
	{
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	public boolean validateToken(String authToken)
	{
		try
		{
			Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(authToken);

			return true;
		}
		catch (MalformedJwtException ex)
		{
			logger.error("Invalid JWT token");
		}
		catch (ExpiredJwtException ex)
		{
			logger.error("Expired JWT token");
		}
		catch (UnsupportedJwtException ex)
		{
			logger.error("Unsupported JWT token");
		}
		catch (IllegalArgumentException ex)
		{
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
