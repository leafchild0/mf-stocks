package com.poc.authserver.web;

import java.net.URI;

import javax.validation.Valid;

import com.poc.authserver.HasLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.poc.authserver.jwt.JwtTokenProvider;
import com.poc.authserver.model.User;
import com.poc.authserver.service.CustomUserDetailsService;
import com.poc.authserver.web.dto.JwtAuthenticationResponse;
import com.poc.authserver.web.dto.LoginDTO;
import com.poc.authserver.web.dto.RegisterDTO;

@RestController
@RequestMapping
public class AuthController implements HasLogger
{
	final AuthenticationManager authenticationManager;

	final CustomUserDetailsService customUserDetailsService;

	final JwtTokenProvider tokenProvider;

	public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtTokenProvider tokenProvider)
	{
		this.authenticationManager = authenticationManager;
		this.customUserDetailsService = customUserDetailsService;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO)
	{
		getLogger().info("Login by username " + loginDTO.getUsername());

		// Any auth possible, i.e. SAML and LDAP
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginDTO.getUsername(),
				loginDTO.getPassword()
			)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO)
	{
		getLogger().info("Registering user by username " + registerDTO.getUsername());

		if (customUserDetailsService.existsByUsername(registerDTO.getUsername()))
		{
			return ResponseEntity.badRequest().body("Username is already exists!");
		}

		User result = customUserDetailsService.createNewUser(registerDTO);

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath().path("/api/users/{username}")
			.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body("User registered successfully");
	}
}
