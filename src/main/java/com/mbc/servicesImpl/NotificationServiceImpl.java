package com.mbc.servicesImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.mbc.services.NotificationService;
import com.mbc.utility.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class NotificationServiceImpl implements NotificationService {

	   //Create Exchange
	   @Override
	   public void createExchange(String exchangeName) {
	        try (Connection connection = RabbitMQConfig.getConnection()) {
	        	 Channel channel = connection.createChannel();

	             try {
	                 channel.exchangeDeclarePassive(exchangeName);
	             } catch (IOException e) {
	                 channel = connection.createChannel();
	                 channel.exchangeDeclare(exchangeName, "direct", true, true, null);
	                 
	                 System.out.println("Exchange '" + exchangeName + "' created successfully");
	             } finally {
	                 if (channel != null && channel.isOpen()) {
	                     channel.close();
	                 }
	             }
	        } catch (IOException e1) {
				e1.printStackTrace();
			} catch (TimeoutException e1) {
				e1.printStackTrace();
			}
	    }
	    
	    //Create Queue
	    @Override
	    public void createQueue(String exchangeName,String queueName,String routingKey) {
	        try (Connection connection = RabbitMQConfig.getConnection();
	             Channel channel = connection.createChannel()) {
	        	
	        	Map<String, Object> arguments = new HashMap<String, Object>();
	        	arguments.put("x-message-ttl", 604800000);
	            
	            channel.queueDeclare(queueName, true, false, false, arguments);
	            channel.queueBind(queueName, exchangeName, routingKey);
	            
	            //for sending all devices
	            String allDevicesRoutingKey  = createRoutingKey(exchangeName,"all");
	            channel.queueBind(queueName, exchangeName, allDevicesRoutingKey);
	           
	            System.out.println("Queue '" + queueName + "' created successfully");
	        } catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
	    }
	    
	    //create routing key
	    @Override
	    public String createRoutingKey(String exchangeName,String device) {
			return String.format("%s.%s.%s",exchangeName,"device",device);
		}
	    
	    //Send Message to Queue
	    @Override
	    public void sendMessage(String exchangeName,String message,String routingKey) {
	        try (Connection connection = RabbitMQConfig.getConnection();
	             Channel channel = connection.createChannel()) {
	       
	            channel.basicPublish(exchangeName,
	            					 routingKey,
		            				 MessageProperties.PERSISTENT_TEXT_PLAIN,
		            				 message.getBytes(StandardCharsets.UTF_8)
		            				 );
	            System.out.println("Sent '" + message + "' to exchange '" + 
	                              exchangeName + "' with routing key '" + routingKey + "'");
	        } catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
	    }
	    
	    //Remove Queue
	    @Override
	    public void removeQueue(String queueName) {
			try (Connection connection = RabbitMQConfig.getConnection();
			Channel channel = connection.createChannel()) {
				
			    channel.queueDelete(queueName);
			    
			    System.out.println("Queue '" + queueName + "' removed successfully");
			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	    
	    //Unbind Queue
	    @Override
	    public void unbindQueue(String exchangeName,String queueName,String routingKey) {
			try (Connection connection = RabbitMQConfig.getConnection();
			Channel channel = connection.createChannel()) {
				
			    channel.queueUnbind(queueName, exchangeName, routingKey);
			    
			    //for sending all devices
			    String allDevicesRoutingKey  = createRoutingKey(exchangeName,"all");
			    channel.queueUnbind(queueName, exchangeName, allDevicesRoutingKey);
			    
			    System.out.println("Queue '" + queueName + "' unbound successfully");
			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	    
	    //Rebind Queue
	    @Override
	    public void rebindQueue(String exchangeName,String queueName,String routingKey) {
			try (Connection connection = RabbitMQConfig.getConnection();
			Channel channel = connection.createChannel()) {
				
				 channel.queueBind(queueName, exchangeName, routingKey);
				 
				 //for sending all devices
				 String allDevicesRoutingKey  = createRoutingKey(exchangeName,"all");
		         channel.queueBind(queueName, exchangeName, allDevicesRoutingKey);
			    
			    System.out.println("Queue '" + queueName + "' rebound successfully");
			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	    
	    //exchange is exists or not			
		@Override
		public boolean isExists(String exchangeName) {
			try (Connection connection = RabbitMQConfig.getConnection();
			         Channel channel = connection.createChannel()) {
			        
			        channel.exchangeDeclarePassive(exchangeName);
			        return true;
			        
			    } catch (IOException e) {
			    	e.printStackTrace();
			    	return false;
			    } catch (TimeoutException e) {
			    	e.printStackTrace();
			    	return false;
			    } catch (Exception e) {
			    	e.printStackTrace();
			    	return false;
			    }
		}
	    
	    
	    
}
