package com.mbc.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.mbc.model.NotificationMessage;
import com.mbc.services.NotificationHistoryService;
import com.mbc.services.NotificationService;
import com.mbc.servicesImpl.NotificationHistoryServiceImpl;
import com.mbc.servicesImpl.NotificationServiceImpl;
import com.mbc.utility.ResponseUtils;


@WebServlet("/send")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,      // 1MB
	    maxFileSize = 1024 * 1024 * 2,        // 2MB
	    maxRequestSize = 1024 * 1024 * 10     // 10MB
	)
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private NotificationService notificationService;
	private NotificationHistoryService notificationHistoryService;
	private Properties config ;
	
	
	
	@Override
    public void init() {
        this.notificationService = new NotificationServiceImpl();
        this.notificationHistoryService = new NotificationHistoryServiceImpl();
        this.config = (Properties) getServletContext().getAttribute("config");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
			 
			 
			 Part filePart = request.getPart("image");
	            String imageFileName = null;
	            String imagePath = null;
	            
	            if (filePart != null && filePart.getSize() > 0) {
	                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	                
	          
	                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
	                File uploadDir = new File(uploadPath);
	                if (!uploadDir.exists()) uploadDir.mkdir();
	                
	                String filePath = uploadPath + File.separator + imageFileName;
	                filePart.write(filePath);
	                
	                System.out.println("Flle path: " +filePath);
	                imagePath = filePath;
	            }
	            
			 	NotificationMessage message = new NotificationMessage.Builder()
		        	    .exchangeName(request.getParameter("exchangeName"))
		        	    .device(request.getParameter("device"))
		        	    .title(request.getParameter("title"))
		        	    .text(request.getParameter("text"))
		        	    .notificationName(request.getParameter("notificationName"))
		        	    .imageUrl(imagePath)
		        	    .timestamp(new Date())
		        	    .build();			 		     	 			     	 				    
		   	 	
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
		     	 	String text = new Gson().toJson(message);
		     	 	
					String routingKey = String.format("%s.%s.%s",message.getExchangeName(),"device",message.getDevice());				
					
					notificationService.sendMessage(message.getExchangeName(), text,routingKey);		
					
					String apiUrl = config.getProperty("external.api") + String.format("/saveNotificationHistoryToAll?title=%s&body=%s",message.getTitle(),message.getText());
					String apiKey = config.getProperty("external.apiKey");
					
					notificationHistoryService.sendNotificationHistroyToAll(apiUrl, apiKey);
					
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
