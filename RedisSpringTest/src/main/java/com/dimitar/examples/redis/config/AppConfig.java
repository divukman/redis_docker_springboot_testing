package com.dimitar.examples.redis.config;

import com.dimitar.examples.redis.database.redis.listeners.ExpirationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@Slf4j
public class AppConfig {

    @Bean(name="jedisConnectionFactory")
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

	/*@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory
				= new JedisConnectionFactory();
		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(6379);
		return jedisConFactory;
	}*/

    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    RedisMessageListenerContainer keyExpirationListenerContainer(@Qualifier("jedisConnectionFactory") RedisConnectionFactory connectionFactory,
                                                                 ExpirationListener expirationListener) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.addMessageListener(expirationListener, new PatternTopic("__keyevent@*__:expired"));
        listenerContainer.setErrorHandler(e -> log.error("There was an error in redis key expiration listener container", e));

        listenerContainer.addMessageListener((message, pattern) -> {

            System.out.println(message);

        }, new PatternTopic("*"));

        System.out.println("listeners setup");

        return listenerContainer;
    }
}
