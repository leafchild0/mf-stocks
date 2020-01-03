package com.leafchild0.authserver.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CachedAuthenticationProvider implements AuthenticationProvider {

	@Value("salt")
	private String salt;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
			throw new UsernameNotFoundException("Authentication failed for " + name);
		}

		// Send requires to the server and get token

		if (isAuthenticated(password, user)) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, password,
					grantedAuthorities);
			auth.setDetails(user.getId());

			return auth;
		}
		else {
			throw new BadCredentialsException("Username/Password combination is incorrect");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
