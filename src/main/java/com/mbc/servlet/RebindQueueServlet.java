package com.mbc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mbc.model.RebindQueueRequest;
import com.mbc.services.NotificationService;
import com.mbc.servicesImpl.NotificationServiceImpl;
import com.mbc.utility.ResponseUtils;

@WebServlet("/rebindQueue")
public class RebindQueueServlet extends HttpServlet {
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
	        RebindQueueRequest data = gson.fromJson(jsonBody, RebindQueueRequest.class);	   	        	        
	        
	        //unbind Exchange
	        String exchangeToUnbind = data.getOldExchange().contains("_") ? 
	        							data.getOldExchange().split("_")[0] : 
	        								String.format("%s_%s",data.getOldExchange(),data.getUserId());																				 
	        
	        notificationService.unbindQueue(data.getOldExchange(), 
												data.getQueue(), 
												notificationService.createRoutingKey(data.getOldExchange(),data.getDevice())
												);
	        notificationService.unbindQueue(exchangeToUnbind, 
												data.getQueue(), 
												notificationService.createRoutingKey(exchangeToUnbind,data.getDevice())
												);
	        	        	        
	        //create if exchange not exists 
	        notificationService.createExchange(data.getNewExchange());        
	        
	        //rebind Queue
	        String renewRoutingKey = String.format("%s.%s.%s",data.getNewExchange(),"device",data.getDevice());
	        notificationService.rebindQueue(data.getNewExchange(), data.getQueue(), renewRoutingKey);
	                
	        ResponseUtils.sendSuccess(
				    			response,
				    			"rebound queue successfully"
								);
		} catch (Exception e) {		
			ResponseUtils.sendError(
							    response,
							    HttpServletResponse.SC_BAD_REQUEST,
							    "Error while rebinding exchange and queue: " + e.getMessage()
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

}
