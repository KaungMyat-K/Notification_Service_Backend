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
import com.mbc.servicesImpl.NotificationServiceImpl;
import com.mbc.utility.ResponseUtils;


@WebServlet("/setup")
public class NotificationSetupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private NotificationService notificationService;
		
	@Override
	public void init() {
	     this.notificationService = new NotificationServiceImpl();
	}
	
	
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
	        
	        if(isNullOrEmpty(data.getClientId()) ||
	           isNullOrEmpty(data.getUserId()) ||
	           isNullOrEmpty(data.getDevice())
	          ) {
	        	ResponseUtils.sendError(
								    response,
								    HttpServletResponse.SC_BAD_REQUEST,
								    "Error while creating exchange and queue: Required fields are missing or empty"
	        					);
	            return;
	        }
	        
	        data.setDevice(data.getDevice().toLowerCase());
	        
	        if(!data.getDevice().equals("ios") && !data.getDevice().equals("android")) {
	        	ResponseUtils.sendError(
								    response,
								    HttpServletResponse.SC_BAD_REQUEST,
								    "Error while creating exchange and queue: device format is wrong"
								);
	        	return;
	        }        
	        
	        String exchangeName = data.getClientId()+"_"+data.getUserId();	       	        
	        
	        //create Exchange
	        notificationService.createExchange(data.getClientId());
	        notificationService.createExchange(exchangeName);
	        
	        //create Queue
	        notificationService.createQueue(data.getClientId(),
	        								data.getGeneratedId(),
	        								notificationService.createRoutingKey(data.getClientId(), data.getDevice())
	        								);
	        notificationService.createQueue(exchangeName,
	        								data.getGeneratedId(),
	        								notificationService.createRoutingKey(exchangeName, data.getDevice())
	        								);
	        	        
	        ResponseUtils.sendSuccess(
	        	    			response,
	        	    			"created notification setup successfully"
	        					);
		} catch (Exception e) {
			ResponseUtils.sendError(
						    response,
						    HttpServletResponse.SC_BAD_REQUEST,
						    "Error while creating exchange and queue: " + e.getMessage()
						);
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
	
    private boolean isNullOrEmpty(String value) {
	    return value == null || value.trim().isEmpty();
	}

}
