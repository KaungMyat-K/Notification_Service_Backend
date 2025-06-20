package com.mbc.model;

public class RebindQueueRequest {

	private String oldExchange;
	private String queue;
	private String device;
	private String newExchange;
	private String userId;
	
	public String getOldExchange() {
		return oldExchange;
	}
	public void setOldExchange(String oldExchange) {
		this.oldExchange = oldExchange;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getNewExchange() {
		return newExchange;
	}
	public void setNewExchange(String newExchange) {
		this.newExchange = newExchange;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
			
}
