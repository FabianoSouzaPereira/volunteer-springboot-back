package com.fabianospdev.volunteer.messenger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer{

    @KafkaListener(topics="user-registration-events")
    public void listen(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}