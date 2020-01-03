package com.leafchild0.authclient.configuration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.leafchild0.authclient.Helper;
import com.leafchild0.authclient.RestResponses;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * Get token from each response, parse it and populate roles for the user
 */
public class JwtAuthorizationFilter extends GenericFilterBean
{
	private static final String JWT_SECRET = "@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeThWmYq3t6w9z$C&";
	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
	{
		String token = request.getHeader("Authorization");
		if (!StringUtils.isEmpty(token) && token.startsWith("Bearer "))
		{
			try
			{
				byte[] signingKey = JWT_SECRET.getBytes();

				Jws<Claims> parsedToken = Jwts.parser()
					.setSigningKey(signingKey)
					.parseClaimsJws(token.replace("Bearer ", ""));

				String username = parsedToken.getBody().getSubject();
				String userId = parsedToken.getHeader().get("user_id").toString();

				List<GrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
					.get("role")).stream().map(role -> (String) role)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

				if (!StringUtils.isEmpty(username))
				{
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
					auth.setDetails(userId);
					return auth;
				}
			}
			catch (ExpiredJwtException exception)
			{
				log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			}
			catch (UnsupportedJwtException exception)
			{
				log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			}
			catch (MalformedJwtException exception)
			{
				log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			}
			catch (IllegalArgumentException exception)
			{
				log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			}
			catch (SignatureException exception)
			{
				log.warn("Request to parse JWT with wrong signature : {} failed : {}", token, exception.getMessage());
			}
		}

		return null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		Authentication authentication = getAuthentication(servletRequest);
		if (authentication == null)
		{
			Helper.formResponse(response, RestResponses.AUTHORIZATION_FAILED);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}
