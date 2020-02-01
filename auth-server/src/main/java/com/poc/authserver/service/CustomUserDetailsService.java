package com.poc.authserver.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poc.authserver.model.Role;
import com.poc.authserver.model.RoleName;
import com.poc.authserver.model.User;
import com.poc.authserver.repository.RoleRepository;
import com.poc.authserver.repository.UserRepository;
import com.poc.authserver.security.UserPrincipal;
import com.poc.authserver.web.dto.RegisterDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	UserRepository userRepository;

	RoleRepository roleRepository;

	PasswordEncoder passwordEncoder;

	public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder)
	{
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDetails loadUserById(Long userId)
	{
		return UserPrincipal
			.create(userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Cannot find user by id: " + userId)));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return UserPrincipal
			.create(userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Cannot find user by username: " + username)));
	}

	public boolean existsByUsername(String username)
	{
		return userRepository.findByUsername(username).isPresent();
	}

	public User createNewUser(RegisterDTO registerDTO)
	{
		User user = new User(registerDTO.getUsername(),
			registerDTO.getEmail(), registerDTO.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
			.orElseThrow(() -> new RuntimeException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));
		user.setEnabled(true);

		return userRepository.save(user);
	}
}
