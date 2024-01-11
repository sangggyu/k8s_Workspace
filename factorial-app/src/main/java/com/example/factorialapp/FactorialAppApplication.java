package com.example.factorialapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FactorialAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactorialAppApplication.class, args);
	}

}
