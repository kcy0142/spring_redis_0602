package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.config.RedisConfig;

@SpringBootApplication
public class SpringBootRedisApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SpringBootRedisApplication.class, args);
		
		Object[] sources = {SpringBootRedisApplication.class,RedisConfig.class};
		SpringApplication.run(sources, args);

	}
}
