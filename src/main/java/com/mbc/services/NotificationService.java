package com.mbc.services;

public interface NotificationService {

	public void createExchange(String exchangeName);
	public void createQueue(String exchangeName,String queueName,String routingKey);
	public String createRoutingKey(String exchangeName,String device);
	public void sendMessage(String exchangeName,String message,String routingKey);
	public void removeQueue(String queueName);
	public void unbindQueue(String exchangeName,String queueName,String routingKey);
	public void rebindQueue(String exchangeName,String queueName,String routingKey);
	public boolean isExists(String exchangeName);
}
