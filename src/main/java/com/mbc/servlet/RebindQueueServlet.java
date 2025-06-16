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

@WebServlet("/rebindQueue")
public class RebindQueueServlet extends HttpServlet {
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
	        RebindQueueRequest data = gson.fromJson(jsonBody, RebindQueueRequest.class);	   
	        
	        String routingKey = String.format("%s.%s.%s",data.getFrom(),"device",data.getDevice());
	        
	        //unbind Exchange
	        NotificationService.unbindQueue(data.getFrom(), data.getQueue(), routingKey);
	        
	        //rebind Queue
	        String renewRoutingKey = String.format("%s.%s.%s",data.getTo(),"device",data.getDevice());
	        NotificationService.rebindQueue(data.getTo(), data.getQueue(), renewRoutingKey);
	                
	        response.setContentType("application/json");
	        response.getWriter().write("{\"status\": \"rebind successfully\"}");
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error rebinding exchange and queue: " + e.getMessage());
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
