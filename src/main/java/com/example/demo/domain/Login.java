package com.example.demo.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;



public class Login implements Serializable {


	
	private static final long serialVersionUID = 1L;
	
	
		private String id;
	
		
		private String pw;

		
		private String database;

		public String getDatabase() {
			return database;
		}


		public void setDatabase(String database) {
			this.database = database;
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getPw() {
			return pw;
		}


		public void setPw(String pw) {
			this.pw = pw;
		}
		
		
		
}
