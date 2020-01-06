package com.poc.authserver.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.poc.authserver.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider
{
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${auth-server.jwtSecret}")
	private String jwtSecret;

	@Value("${auth-server.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication)
	{

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder()
			.setSubject(Long.toString(userPrincipal.getId()))
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(getKey())
			.compact();
	}

	public Long getUserIdFromJWT(String token)
	{
		Claims claims = Jwts.parser()
			.setSigningKey(getKey())
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
			Jwts.parser()
				.setSigningKey(getKey())
				.parseClaimsJws(authToken);

			return true;
		}
		catch (SignatureException ex)
		{
			logger.error("Invalid JWT signature");
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
