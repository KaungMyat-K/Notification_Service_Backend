package com.noti.utility;

import java.nio.charset.StandardCharsets;

import com.noti.model.PushSubscription;
import com.noti.services.PushNotificationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class NotificationConsumer {
	
	    private final PushNotificationService notificationService;
	    private final SubscriptionRepository subscriptionRepo;
	    
	    public NotificationConsumer() {
	        this.notificationService = new PushNotificationService();
	        this.subscriptionRepo = new SubscriptionRepository();
	    }
	    
	    public void start() throws Exception {
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();
	        
	        channel.exchangeDeclare("c1", "direct", true, true, null);
	        channel.queueDeclare("u1", false, false, false, null);
	        channel.queueBind("u1", "c1", RabbitMQConfig.getRoutingKey());
	        
	        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
	            System.out.println("Received message: " + message);
	            
	            // Send to all subscribers
	            for (PushSubscription subscription : subscriptionRepo.getAll()) {
	                try {
	                    notificationService.sendNotification(subscription, message);
	                } catch (Exception e) {
	                    System.err.println("Failed to send to " + subscription.getEndpoint());
	                    e.printStackTrace();
	                }
	            }
	        };
	        
	        channel.basicConsume("u1", true, deliverCallback, consumerTag -> {});
	    }
}
