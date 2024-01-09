package com.example.factorialcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FactorialCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactorialCacheApplication.class, args);
	}

}
