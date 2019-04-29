package com.bridgelabz.registerLoginService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
public class RegisterLoginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterLoginServiceApplication.class, args);
	}

}
