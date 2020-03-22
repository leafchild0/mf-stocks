package com.poc.data;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController
{
	@GetMapping("/user")
	//	@Secured("ROLE_USER")
	public ResponseEntity getFullUserInfo()
	{
		return ResponseEntity.ok("I am User!");
	}

	@GetMapping("/admin")
	public ResponseEntity getAdmin()
	{
		return ResponseEntity.ok("I am Administrator!");
	}
}
