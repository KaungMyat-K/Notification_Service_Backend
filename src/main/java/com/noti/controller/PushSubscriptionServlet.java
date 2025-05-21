package com.noti.controller;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noti.model.PushSubscription;
import com.noti.utility.SubscriptionRepository;



@WebServlet("/api/push/subscribe")
public class PushSubscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SubscriptionRepository subscriptionRepo = new SubscriptionRepository();

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 System.out.println("enter sub");
		try (JsonReader reader = Json.createReader(request.getReader())) {
            JsonObject subscriptionJson = reader.readObject();
            JsonObject keys = subscriptionJson.getJsonObject("keys");
            
            PushSubscription subscription = new PushSubscription(
                subscriptionJson.getString("endpoint"),
                keys.getString("p256dh"),
                keys.getString("auth")
            );
           
            // Store subscription
            new SubscriptionRepository().save(subscription);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
	}
	
	 @Override
	    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        setCorsHeaders(response);
	        response.setStatus(HttpServletResponse.SC_OK);
	    }

	    private void setCorsHeaders(HttpServletResponse response) {
	        // Allow specific origin instead of * for production
	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8100");
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setHeader("Access-Control-Max-Age", "3600");
	    }

}
