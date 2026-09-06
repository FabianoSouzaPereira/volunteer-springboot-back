package com.fabianospdev.volunteer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class VolunteerApplication{

    public static void main(String[] args) {
        SpringApplication.run(VolunteerApplication.class, args);
    }

}