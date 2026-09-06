package com.fabianospdev.volunteer.messaging;

public interface DomainEventPublisher {

    void publish(String topic, String payload);
}
