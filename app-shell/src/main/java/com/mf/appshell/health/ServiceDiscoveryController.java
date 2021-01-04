package com.mf.appshell.health;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * An example of custom health check for Consul
 */
@RestController
public class ServiceDiscoveryController {

	@GetMapping("/health-check")
	public ResponseEntity<String> myCustomCheck() {
		String message = "Testing my healh check function";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
