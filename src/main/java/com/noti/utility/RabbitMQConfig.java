package com.noti.utility;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.coyote.BadRequestException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
	
	private static final String HOST = "localhost";
    private static final int PORT = 5672;
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String EXCHANGE_NAME = "notifications_exchange";
    private static final String QUEUE_NAME = "notifications_queue";
    private static final String ROUTING_KEY = "notification.key";
    
    public static String getExchangeName() {
        return EXCHANGE_NAME;
    }
    
    public static String getQueueName() {
        return QUEUE_NAME;
    }
    
    public static String getRoutingKey() {
        return ROUTING_KEY;
    }

    public static Connection getConnection() throws IOException, TimeoutException {
    	
    		ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername(USERNAME);
            factory.setPassword(PASSWORD);
            
            return factory.newConnection();
    }

//    public static void declareExchangeAndQueue() throws IOException, TimeoutException {
//        try (Connection connection = getConnection();
//             Channel channel = connection.createChannel()) {
//            
//            
//            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
//            
//            
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//            
//           
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
//        }
//    }
}
