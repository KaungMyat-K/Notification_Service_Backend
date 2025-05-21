package com.noti.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.noti.model.NotificationMessage;
import com.noti.utility.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class NotificationService {
	
	 
	   //Create Exchange
	   public static void createExchange(String exchangeName) 
	            							throws IOException, TimeoutException {
	        try (Connection connection = RabbitMQConfig.getConnection()) {
	        	 Channel channel = connection.createChannel();

	             try {
	                 channel.exchangeDeclarePassive(exchangeName);
	             } catch (IOException e) {
	                 channel = connection.createChannel();
	                 channel.exchangeDeclare(exchangeName, "direct", true, true, null);
	             } finally {
	                 if (channel != null && channel.isOpen()) {
	                     channel.close();
	                 }
	             }
	        }
	    }
	    
	    //Create Queue
	    public static void createQueue(String exchangeName,String queueName) 
	                                		 throws IOException, TimeoutException {
	        try (Connection connection = RabbitMQConfig.getConnection();
	             Channel channel = connection.createChannel()) {
	        	
	        	Map<String, Object> arguments = new HashMap<String, Object>();
	        	arguments.put("x-message-ttl", 604800000);
	            
	            channel.queueDeclare(queueName, true, false, false, arguments);
	            channel.queueBind(queueName, exchangeName, RabbitMQConfig.getRoutingKey());
	            System.out.println("Queue '" + queueName + "' created successfully");
	        }
	    }
	    
	    //Send Message to Queue
	    public static void sendMessage(String exchangeName,String message) 
	            								throws IOException, TimeoutException {
	        try (Connection connection = RabbitMQConfig.getConnection();
	             Channel channel = connection.createChannel()) {
	       
	            channel.basicPublish(exchangeName,
	            					 RabbitMQConfig.getRoutingKey(),
		            				 MessageProperties.PERSISTENT_TEXT_PLAIN,
		            				 message.getBytes(StandardCharsets.UTF_8)
		            				 );
	            System.out.println("Sent '" + message + "' to exchange '" + 
	                              exchangeName + "' with routing key '" + RabbitMQConfig.getRoutingKey() + "'");
	        }
	    }
	    
	    
	    
}
