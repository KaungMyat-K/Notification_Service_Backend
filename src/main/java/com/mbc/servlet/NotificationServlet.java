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
import com.mbc.servicesImpl.NotificationServiceImpl;
import com.mbc.utility.ResponseUtils;


@WebServlet("/send")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private NotificationService notificationService;
	
	@Override
    public void init() {
        this.notificationService = new NotificationServiceImpl();
    }
	
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
		        
		     	 	System.out.println(request.getParameter("image"));
		     	 	
		     	 	if(isNullOrEmpty(message.getExchangeName()) ||
		     	 	   isNullOrEmpty(message.getDevice()) ||
		     	 	   isNullOrEmpty(message.getTitle()) ||
		     	 	   isNullOrEmpty(message.getText()) ||
		     	 	   isNullOrEmpty(message.getNotificationName())		     	 	   
		    	      ) {
		     	 		ResponseUtils.sendError(
							    response,
							    HttpServletResponse.SC_BAD_REQUEST,
							    "Error while sending notification: Required fields are missing or empty"
        					);
		     	 		return;
		     	 	}
		     	 		
		     	 	if(!notificationService.isExists(message.getExchangeName())) {
							ResponseUtils.sendError(
										    response,
										    HttpServletResponse.SC_BAD_REQUEST,
										    "Error while sending notification: Exchange "+message.getExchangeName()+" not exists"
										);
							return;
						}	
		     	 		
					String routingKey = String.format("%s.%s.%s",message.getExchangeName(),"device",message.getDevice());
					
					
					
					//notificationService.sendMessage(message.getExchangeName(), text,routingKey);
					
					ResponseUtils.sendSuccess(
				        	    			response,
				        	    			"send message successfully"
				        					);
					
	        } catch (Exception e) {
	        	ResponseUtils.sendError(
								    response,
								    HttpServletResponse.SC_BAD_REQUEST,
								    "Error while sending notification: " + e.getMessage()
								);	      
	            e.printStackTrace();
	        }	
	}
	
	private boolean isNullOrEmpty(String value) {
	    return value == null || value.trim().isEmpty();
	}

}
