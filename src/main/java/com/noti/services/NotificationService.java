package com.noti.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.noti.model.NotificationMessage;
import com.noti.utility.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class NotificationService {
	
	 public static void sendNotification(NotificationMessage message) 
	            throws IOException, TimeoutException {
	        
	        try (Connection connection = RabbitMQConfig.getConnection();
	             Channel channel = connection.createChannel()) {
	            
	            String jsonMessage = new Gson().toJson(message);
	            
	            channel.basicPublish(
	                RabbitMQConfig.getExchangeName(),
	                RabbitMQConfig.getRoutingKey(),
	                MessageProperties.PERSISTENT_TEXT_PLAIN,
	                jsonMessage.getBytes(StandardCharsets.UTF_8)
	            );
	            
	            System.out.println(" [x] Sent '" + jsonMessage + "'");
	        }
	    }
}
