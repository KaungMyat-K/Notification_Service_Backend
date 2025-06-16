package com.mbc.utility;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConfigLoaderListener implements ServletContextListener {
	
	 @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        try (InputStream input = Thread.currentThread()
	                                       .getContextClassLoader()
	                                       .getResourceAsStream("config.properties")) {
	            Properties props = new Properties();
	            props.load(input);

	            sce.getServletContext().setAttribute("config", props);

	            RabbitMQConfig.setConfig(props);
	            
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to load config.properties", e);
	        }
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	       
	    }
}
