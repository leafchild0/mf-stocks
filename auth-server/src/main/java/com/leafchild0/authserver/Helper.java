package com.leafchild0.authserver;

import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.JsonObject;

public class Helper
{

	public static String cacheData(String data, String salt) {

		return DigestUtils.sha512Hex(data + salt);
	}

	public static void formResponse(ServletResponse response, RestResponses responseData) throws IOException {

		formResponse(response, responseData, null);
	}

	public static void formResponse(ServletResponse response,
			RestResponses responseData,
			String token) throws IOException {

		HttpServletResponse mappedResponse = (HttpServletResponse) response;
		mappedResponse.setStatus(responseData.getStatusCode());
		mappedResponse.setContentType("application/json");
		mappedResponse.setCharacterEncoding("UTF-8");

		JsonObject json = new JsonObject();
		json.addProperty("timestamp", new Timestamp(System.currentTimeMillis()).toString());

		if (responseData.getStatusCode() >= 400) {
			json.addProperty("status", "Error");
		}

		json.addProperty("message", responseData.getMessage());

		if (token != null) {
			json.addProperty("token", token);
		}

		Writer writer = mappedResponse.getWriter();
		writer.write(json.toString());
		writer.flush();
		writer.close();
	}

}
