package com.example.demo.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;



public class Car implements Serializable {


	
	private static final long serialVersionUID = 1L;
	
	
		private String id;
	
		
		private String make;
		
		private String model;
		
		private String color;
		
		private int topSpeed;

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getMake() {
			return make;
		}

		public void setMake(String make) {
			this.make = make;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public int getTopSpeed() {
			return topSpeed;
		}

		public void setTopSpeed(int topSpeed) {
			this.topSpeed = topSpeed;
		}

		
		
		
}
