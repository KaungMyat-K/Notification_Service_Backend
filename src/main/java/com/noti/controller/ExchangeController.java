package com.noti.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.noti.services.NotificationService;


@WebServlet("/createExchange")
public class ExchangeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static class ReqData{
			String clientId;
			String userId;
			String generatedId;
	};


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			StringBuilder sb = new StringBuilder();
	        BufferedReader reader = request.getReader();
	        String line;

	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }

	        String jsonBody = sb.toString();

	        Gson gson = new Gson();
	        ReqData data = gson.fromJson(jsonBody, ReqData.class);
	        
	        //create Exchange
	        NotificationService.createExchange(data.clientId);
	        NotificationService.createExchange(data.userId);
	        
	        //create Queue
	        NotificationService.createQueue(data.clientId,data.generatedId);
	        NotificationService.createQueue(data.userId, data.generatedId);
	        
	        response.setContentType("application/json");
	        response.getWriter().write("{\"status\": \"created successfully\"}");
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error creating exchange and queue: " + e.getMessage());
            e.printStackTrace();
		}
	}
	
	@Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        setCorsHeaders(resp);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
