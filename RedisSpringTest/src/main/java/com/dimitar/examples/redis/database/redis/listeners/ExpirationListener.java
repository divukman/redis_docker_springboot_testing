package com.dimitar.examples.redis.database.redis.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExpirationListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String key = new String(message.getBody());
        log.debug("expired key: {}", key);
        System.out.println("expired key: " +key);
    }

}
