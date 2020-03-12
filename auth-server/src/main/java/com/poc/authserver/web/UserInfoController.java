package com.poc.authserver.web;

import com.poc.authserver.HasLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.authserver.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/users")
public class UserInfoController implements HasLogger
{
	final CustomUserDetailsService customUserDetailsService;

	public UserInfoController(CustomUserDetailsService customUserDetailsService)
	{
		this.customUserDetailsService = customUserDetailsService;
	}

	@GetMapping("/{username}")
	@Secured("ROLE_USER")
	public ResponseEntity getFullUserInfo(@PathVariable String username)
	{
		getLogger().info("Getting user by username " + username);

		return ResponseEntity.ok(customUserDetailsService.loadUserByUsername(username));
	}
}
