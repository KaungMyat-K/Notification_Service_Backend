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

    private static final String ROUTING_KEY = "notification.key";
    
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

}
