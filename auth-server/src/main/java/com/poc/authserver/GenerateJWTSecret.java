package com.poc.authserver;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

/**
 * Generates new secret for HS384 algorithm
 */
public class GenerateJWTSecret
{
	public static void main(String[] args)
	{
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS384);
		String secretBase64Key = Encoders.BASE64.encode(key.getEncoded());

		System.out.println(secretBase64Key);
	}
}
