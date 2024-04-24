package com.fabianospdev.volunteer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class VolunteerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolunteerApplication.class, args);
	}

}