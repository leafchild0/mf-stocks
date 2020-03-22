package com.poc.gateway.security;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain)
		throws ServletException, IOException {
		String token = getJwtFromRequest(req);
		if (StringUtils.hasText(token) && tokenProvider.validateToken(token))
		{
			try {
				Claims claims = tokenProvider.getClaims(token);
				Long userId = tokenProvider.getUserIdFromJWT(claims);

//				String username = claims.getSubject();
				@SuppressWarnings("unchecked")
				List<String> authorities = claims.get("authorities", List.class);
				if (userId != null) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null,
						authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (Exception ignore) {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(req, rsp);
	}

	private String getJwtFromRequest(HttpServletRequest request)
	{
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
		{
			return bearerToken.substring(7);
		}
		return null;
	}
}