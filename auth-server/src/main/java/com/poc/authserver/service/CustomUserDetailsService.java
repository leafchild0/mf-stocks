package com.poc.authserver.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poc.authserver.model.Role;
import com.poc.authserver.model.RoleName;
import com.poc.authserver.model.User;
import com.poc.authserver.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	// Hardcoded user, for demo purposes
	private User user = new User(1L,
		"1",
		"1@2",
		"$2a$10$OZThCVKbYkTPCxtw5kMtPO9JbiBbxX64oPySasLZpipvFNVWrW7di",
		new HashSet<>(Arrays.asList(
			new Role(1L, RoleName.ROLE_ADMIN)
		))
	);

	public UserDetails loadUserById(Long userId)
	{
		return UserPrincipal.create(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return UserPrincipal.create(user);
	}
}
