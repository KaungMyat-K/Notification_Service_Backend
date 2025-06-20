package com.mbc.utility;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ResponseUtils {
	
	private static final Gson GSON = new Gson();

	public static void sendJsonResponse(HttpServletResponse response,
							            int statusCode,
							            String status,
							            String message,
							            Map<String, Object> additionalData) throws IOException {
		response.setStatus(statusCode);
		response.setContentType("application/json");
							
		Map<String, Object> responseMap = new LinkedHashMap<>();
							responseMap.put("status", status);
							responseMap.put("message", message);
							
		if (additionalData != null) {
			responseMap.putAll(additionalData);
		}
							
		try {
			response.getWriter().write(GSON.toJson(responseMap));
			} catch (IOException e) {					
				response.setContentType("text/plain");
				response.getWriter().write(message);
				throw e;
			}
	}							
							
	public static void sendError(HttpServletResponse response,
							     int statusCode,
							     String message) throws IOException {
			sendJsonResponse(response, statusCode, "error", message, null);
	}
							
	public static void sendSuccess(HttpServletResponse response,
							       String message) throws IOException {
			sendJsonResponse(response, HttpServletResponse.SC_OK, "success", message, null);
	}
}
