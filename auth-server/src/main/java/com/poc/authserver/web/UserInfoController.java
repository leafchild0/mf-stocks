package com.poc.authserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.authserver.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/users")
public class UserInfoController
{
	@Autowired CustomUserDetailsService customUserDetailsService;

	@GetMapping("/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity getFullUserInfo(@PathVariable Long id)
	{
		return ResponseEntity.ok(customUserDetailsService.loadUserById(id));
	}
}
