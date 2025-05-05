package com.noti.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noti.model.NotificationMessage;
import com.noti.services.NotificationService;
import com.noti.utility.RabbitMQConfig;


@WebServlet("/noti")
public class NotificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	        NotificationMessage message = new NotificationMessage.Builder()
	        	    .projectId(request.getParameter("projectId"))
	        	    .clientId(request.getParameter("clientId"))
	        	    .title(request.getParameter("title"))
	        	    .text(request.getParameter("text"))
	        	    .imageUrl(request.getParameter("imageUrl"))
	        	    .senderName(request.getParameter("senderName"))
	        	    .timestamp(new Date())
	        	    .build();
	        try {	        
	            RabbitMQConfig.declareExchangeAndQueue();	                    
	            NotificationService.sendNotification(message);	            
	            response.getWriter().write("Notification sent successfully!");
	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Error sending notification: " + e.getMessage());
	            e.printStackTrace();
	        }	
	  }

}
