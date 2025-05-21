package com.noti.utility;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PushInitializer implements ServletContextListener {

private NotificationConsumer consumer;
    
    public void contextInitialized(ServletContextEvent sce) {
        try {
            consumer = new NotificationConsumer();
            consumer.start();
            System.out.println("Push notification consumer started");
        } catch (Exception e) {
            throw new RuntimeException("Failed to start notification consumer", e);
        }
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
       
    }
}
