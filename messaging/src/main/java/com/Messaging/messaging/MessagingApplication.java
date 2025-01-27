package com.Messaging.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages =
		{"com.Messaging.messaging.Config",
		"com.Messaging.messaging.RestController",
		"com.Messaging.messaging.Service"
		})
@EnableJpaRepositories(basePackages = {"com.Messaging.messaging.Repository"})
@EntityScan(basePackages = {"com.Messaging.messaging.Models"})
public class MessagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingApplication.class, args);
	}

}

