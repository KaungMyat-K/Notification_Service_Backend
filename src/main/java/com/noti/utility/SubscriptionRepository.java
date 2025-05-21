package com.noti.utility;

import java.util.ArrayList;
import java.util.List;

import com.noti.model.PushSubscription;

public class SubscriptionRepository {
	
	private List<PushSubscription> subscriptions = new ArrayList<>();
    
    public void save(PushSubscription subscription) {
        subscriptions.add(subscription);
    }
    
    public List<PushSubscription> getAll() {
        return new ArrayList<>(subscriptions);
    }
    
    public void delete(String endpoint) {
        subscriptions.removeIf(sub -> sub.getEndpoint().equals(endpoint));
    }
}
