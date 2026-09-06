package com.fabianospdev.volunteer.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(DomainEventPublisher.class)
public class NoOpEventPublisher implements DomainEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(NoOpEventPublisher.class);

    @Override
    public void publish(String topic, String payload) {
        log.debug("Kafka disabled. Skipping event on topic {}: {}", topic, payload);
    }
}
