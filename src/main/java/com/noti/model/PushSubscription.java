package com.noti.model;

public class PushSubscription {
	private String endpoint;
    private String p256dh;
    private String auth;
    
    
    
	public PushSubscription(String endpoint, String p256dh, String auth) {
		super();
		this.endpoint = endpoint;
		this.p256dh = p256dh;
		this.auth = auth;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getP256dh() {
		return p256dh;
	}
	public void setP256dh(String p256dh) {
		this.p256dh = p256dh;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
    
    
}
