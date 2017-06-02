package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.Login;
import com.example.demo.services.LoginService;

@Repository
public class LoginServiceImpl implements  LoginService
{
	
	@Autowired
	private RedisTemplate redisTemplate;
	
		@Override
		public Set<String>  retrieveDb()
		{
			Set<String> redisKeys = redisTemplate.keys("vpa_*");	
			
			return redisKeys;
				
		}

		
		public void save(Login login){
			
			final String key = String.format("auth_denyed:%s", login.getId());
			final String key1 = String.format("auth_denyed:%s", login.getId());
			
			redisTemplate.delete(key);
			//redisTemplate.opsForHash().delete(key);
			
		//	redisTemplate.opsForHash().put(LOGIN_KEY,login.getId(),login);
			
			redisTemplate.opsForHash().put("auth_denyed",login.getId(),login.getDatabase());
			//redisTemplate.opsForSet().add(key, login.getDatabase());
			
			//redisTemplate.opsForList().leftPush(key1, login.getDatabase());
			
		}
		
		public String findAll(Login login){
			
			final String key = String.format("auth_denyed:%s", login.getId());
			//Map<Object, Object> dbList = redisTemplate.opsForHash().entries(key); 
			//	redisTemplate.opsForHash().put("auth_denyed",login.getId(),login.getDatabase());
			String dbList=(String) redisTemplate.opsForHash().get("auth_denyed", login.getId());
			
			System.out.println("dbList============="+dbList);
			return dbList;
		}
}
