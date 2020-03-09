package com.leafchild0.authclient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author victor
 * @date 09.03.2020
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

	// Login
	@PostMapping(value = "/login")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String login(@RequestBody Map<String, String> payload) {

		System.out.println("Trying to login");
		String name = StringUtils.defaultIfBlank(payload.get("username"), "");
		String password = StringUtils.defaultIfBlank(payload.get("password"), "");

		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)) {
			// Perform call to auth server using RestTemplate and get a token
		}

		return "";

	}

	// Data
	@PostMapping(value = "/data")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getData() {

		System.out.println("Getting data");

		// Check token using Rest template
		// If it's OK, perform


		return Collections.emptyList();

	}
}
