package com.noti.services;

import java.nio.charset.StandardCharsets;

import com.noti.model.PushSubscription;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;

public class PushNotificationService {

	public void sendNotification(PushSubscription subscription, String message) throws Exception {

        PushService pushService = new PushService();
        pushService.setPrivateKey("Uud3YjRJ6zq2fk7_0CBu1DYgaGbNYyYWPIJB2tCWakU");
        pushService.setPublicKey("BOIjPlPEDcVMEBhxA7nPxH92obcXVTo37dpzg_sAJyth7LZjT9lWCkokc9tNhDy8DeFGH6ZA8n1EnvM5B_Rz8S4");
        pushService.setSubject("mailto:your-email@example.com");
        
        Notification notification = new Notification(
            subscription.getEndpoint(),
            subscription.getP256dh(),
            subscription.getAuth(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        
        pushService.send(notification);
    }
}
