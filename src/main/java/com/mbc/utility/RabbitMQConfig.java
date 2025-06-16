package com.mbc.utility;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
  
    private static Properties config;
    
    public static void setConfig(Properties props) {
        config = props;
    }
    
    public static Connection getConnection() throws IOException, TimeoutException {
    	
    		ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(config.getProperty("rabbitmq.host"));
            factory.setPort(Integer.valueOf(config.getProperty("rabbitmq.port")));
            factory.setUsername(config.getProperty("rabbitmq.username"));
            factory.setPassword(config.getProperty("rabbitmq.password"));
            
            return factory.newConnection();
    }
}
