package com.poc.gateway.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


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

//	@Value("${auth-server.jwtExpirationInMs}")
//	private int jwtExpirationInMs;

	public Long getUserIdFromJWT(Claims claims)
	{
		return Long.parseLong(claims.getSubject());
	}

	public Claims getClaims(String token)
	{
		return Jwts.parser()
				.setSigningKey(getKey())
				.parseClaimsJws(token)
				.getBody();
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
