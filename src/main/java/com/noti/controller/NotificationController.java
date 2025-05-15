package com.noti.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.noti.model.NotificationMessage;
import com.noti.services.NotificationService;

@WebServlet("/noti")
public class NotificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try {	        
//		        	String[] prj = {"mbc"};
//		        	String[] client = {"pos","hr","tracking"};	        	
//		        	for (String data : prj) {
//		        		NotificationService.createExchange(data);
//		        		for (String data1 : client) {
//			        			NotificationMessage message = new NotificationMessage.Builder()
//			        	        	    .projectId(data)
//			        	        	    .clientId(data1)
//			        	        	    .title("project name :"+data)
//			        	        	    .text("client name :"+data1)
//			        	        	    .imageUrl(request.getParameter("imageUrl"))
//			        	        	    .senderName("client name :"+data1)
//			        	        	    .timestamp(new Date())
//			        	        	    .build();        			
//			        			String text = new Gson().toJson(message);
//			        			NotificationService.createQueue(data,data1);
//			    	        	NotificationService.sendMessage(data, text);
//						}
//					}
//		        	response.getWriter().write("Notification sent successfully!");
	        	System.out.println("client id "+request.getParameter("clientId"));
	        	 NotificationMessage message = new NotificationMessage.Builder()
	 	        	    .projectId(request.getParameter("projectId"))
	 	        	    .clientId(request.getParameter("clientId"))
	 	        	    .title(request.getParameter("title"))
	 	        	    .text(request.getParameter("text"))
	 	        	    .imageUrl(request.getParameter("imageUrl"))
	 	        	    .senderName("sender : "+request.getParameter("senderName"))
	 	        	    .timestamp(new Date())
	 	        	    .build();
    			String text = new Gson().toJson(message);
    			
    			NotificationService.sendMessage(message.getClientId(), text);
    			
	        	response.getWriter().write("Notification sent successfully!");
		        } catch (Exception e) {
		            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		            response.getWriter().write("Error sending notification: " + e.getMessage());
		            e.printStackTrace();
		        }	
	  }

}
