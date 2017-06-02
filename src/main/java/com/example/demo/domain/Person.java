package com.example.demo.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;



public class Person implements Serializable {


	
	private static final long serialVersionUID = 1L;
	
		
		private String organization;
		
		private String id;
		
		private int idx;
		
		private String name;
		
		private String gender;
		
		private int age;

		public String getId()
		{
				return id;
		}

		public void setId(String id)
		{
				this.id = id;
		}

		public String getName()
		{
				return name;
		}

		public void setName(String name)
		{
				this.name = name;
		}

		

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public int getAge()
		{
				return age;
		}

		public void setAge(int age)
		{
				this.age = age;
		}
		
	
		public String getOrganization() {
			return organization;
		}

		public void setOrganization(String organization) {
			this.organization = organization;
		}
		
		public int getIdx() {
			return idx;
		}

		public void setIdx(int idx) {
			this.idx = idx;
		}
		
}
