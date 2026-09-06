package com.fabianospdev.volunteer.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaEventPublisher implements DomainEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, String payload) {
        try {
            kafkaTemplate.send(topic, payload);
        } catch (Exception ex) {
            log.warn("Failed to publish event to topic {}: {}", topic, ex.getMessage());
        }
    }
}
