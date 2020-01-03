package com.leafchild0.authserver.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.leafchild0.authserver.Helper;
import com.leafchild0.authserver.RestResponses;

/**
 * Filter that performs auth and add a token
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final AuthenticationManager authenticationManager;
	private final Long expirationTime;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, Long expirationTime) {

		super("/login");
		this.authenticationManager = authenticationManager;
		this.expirationTime = expirationTime;

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {

			JsonObject json = JsonParser.parseReader(request.getReader()).getAsJsonObject();
			String username = json.getAsJsonPrimitive("username").getAsString();
			String password = json.getAsJsonPrimitive("password").getAsString();

			if (username.isEmpty() || password.isEmpty()) {
				Helper.formResponse(response, RestResponses.MISSING_USERNAME_OR_PASSWORD);
				return null;
			}
			// Get token from auth-server
			AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					password);
			return authenticationManager.authenticate(authenticationToken);

		}
		catch (NullPointerException e) {
			Helper.formResponse(response, RestResponses.MISSING_USERNAME_OR_PASSWORD);
		}
		catch (MalformedJsonException | JsonSyntaxException e) {
			Helper.formResponse(response, RestResponses.MALFORMED_JSON);
		}

		return null;
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {

		Helper.formResponse(response, RestResponses.AUTHENTICATION_FAILED);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) throws IOException {

		List<String> roles = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		// Use token from auth-server
		String token = "token" + roles.toArray().toString();

		Helper.formResponse(response, RestResponses.AUTHENTICATION_SUCCESS, token);
	}
}
