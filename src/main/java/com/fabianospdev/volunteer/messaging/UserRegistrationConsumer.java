package com.fabianospdev.volunteer.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class UserRegistrationConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationConsumer.class);

    @KafkaListener(topics = KafkaTopics.USER_REGISTRATION)
    public void listen(String message) {
        log.info("Received user registration event: {}", message);
    }
}
