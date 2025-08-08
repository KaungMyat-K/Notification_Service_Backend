package com.mbc.servicesImpl;

import java.net.HttpURLConnection;
import java.net.URL;

import com.mbc.services.NotificationHistoryService;

public class NotificationHistoryServiceImpl implements NotificationHistoryService {
	
	@Override
	public void sendNotificationHistroyToAll(String apiUrl,String apiKey) {
		try {
			URL url = new URL(apiUrl);
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        con.setRequestMethod("POST");
	        con.setRequestProperty("apikey", apiKey); 
	        con.setDoOutput(true);
	        
	        int status = con.getResponseCode();

	        if (status < 200 || status >= 300) {
	            throw new RuntimeException("API call failed with status code: " + status);
	        }

	        con.disconnect();
	        
		} catch (Exception e) {
			throw new RuntimeException("Error while sending notification history: " + e.getMessage());
		}
		
	}

}
