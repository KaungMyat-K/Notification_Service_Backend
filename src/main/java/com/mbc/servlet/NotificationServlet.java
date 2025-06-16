package com.mbc.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mbc.model.NotificationMessage;
import com.mbc.services.NotificationService;


@WebServlet("/send")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {	        
		     	
		     	 NotificationMessage message = new NotificationMessage.Builder()
			        	    .exchangeName(request.getParameter("exchangeName"))
			        	    .device(request.getParameter("device"))
			        	    .title(request.getParameter("title"))
			        	    .text(request.getParameter("text"))
			        	    .notificationName(request.getParameter("notificationName"))
			        	    .timestamp(new Date())
			        	    .build();
		     	 	String text = new Gson().toJson(message);
					String routingKey = String.format("%s.%s.%s",message.getExchangeName(),"device",message.getDevice());
					
					NotificationService.sendMessage(message.getExchangeName(), text,routingKey);
					
					response.setContentType("application/json");
					response.getWriter().write("{\"status\": \"Notification sent successfully!\"}");
	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Error sending notification: " + e.getMessage());
	            e.printStackTrace();
	        }	
	}

}
