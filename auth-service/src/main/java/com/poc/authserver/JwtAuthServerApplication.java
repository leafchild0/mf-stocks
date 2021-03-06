package com.poc.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class for auth service
 */
@SpringBootApplication
@EnableDiscoveryClient
public class JwtAuthServerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(JwtAuthServerApplication.class, args);
	}
}
