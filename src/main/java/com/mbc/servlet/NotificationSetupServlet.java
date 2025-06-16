package com.mbc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mbc.model.NotificationSetupRequest;
import com.mbc.services.NotificationService;


@WebServlet("/setup")
public class NotificationSetupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    

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
	        NotificationSetupRequest data = gson.fromJson(jsonBody, NotificationSetupRequest.class);
	        
	        String exchangeName = data.getClientId()+"_"+data.getUserId();
	        
	        String clientRoutingKey = String.format("%s.%s.%s",data.getClientId(),"device",data.getDevice());
	        
	        String userRoutingKey = String.format("%s.%s.%s",exchangeName,"device",data.getDevice());
	        
	        //create Exchange
	        NotificationService.createExchange(data.getClientId());
	        NotificationService.createExchange(exchangeName);
	        
	        //create Queue
	        NotificationService.createQueue(data.getClientId(),data.getGeneratedId(),clientRoutingKey);
	        NotificationService.createQueue(exchangeName,data.getGeneratedId(),userRoutingKey);
	        
	        
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
