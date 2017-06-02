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
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Car;
import com.example.demo.domain.Cart;
import com.example.demo.services.CarService;
import com.example.queue.RedisMessagePublisher;

import redis.clients.jedis.Pipeline;

@Repository
public class CarServiceImpl implements  CarService
{
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	private static String CAR_KEY = "vpa_cars";
		
		@SuppressWarnings("unchecked")
		@Override
		public int save(Car car)
		{
		////createRedisConnectionFactory.getConnection().select(index)
			
				//redisTemplate.getConnectionFactory().getConnection().select(3);
			
			
				int chk=0;
				final String key_color = String.format("vpa_car_color:%s", car.getColor());
				
				final String key_make = String.format("vpa_car_make:%s", car.getMake());
				
				Car cart=(Car)redisTemplate.opsForHash().get(CAR_KEY, car.getId());
				
				if(cart==null){
					redisTemplate.opsForHash().put(CAR_KEY,car.getId(),car);
					redisTemplate.opsForSet().add(key_color, car.getId());
					redisTemplate.opsForSet().add(key_make, car.getId());
					return 0;
				}else{
					return 1;
				}
				
				
					
		}

	



		@Override
		public Car find(String id)
		{
			Car cart=(Car)redisTemplate.opsForHash().get(CAR_KEY, id);
			return (Car)this.redisTemplate.opsForHash().get(CAR_KEY, id);
		}

		@Override
		public Map<Object,Object> findAll()
		{
			Map<Object, Object> carList = redisTemplate.opsForHash().entries(CAR_KEY); 
			return redisTemplate.opsForHash().entries(CAR_KEY);
		}
		
		@Override
		public Map<Object,Object> findAll1()
		{
			
			Map<Object, Object> carList = redisTemplate.opsForHash().entries(CAR_KEY); 
			return redisTemplate.opsForHash().entries(CAR_KEY);
		}
		
		
		@Override
		public List<Car> findSearchAll(Car car){
			
			Set list1= redisTemplate.opsForSet().intersect( "vpa_car_make:"+car.getMake(), "vpa_car_color:"+car.getColor());
			//Set list2= redisTemplate.opsForSet().union( "car_make:"+car.getMake(), "car_color:"+car.getColor());
			//Set list3= redisTemplate.opsForSet().difference( "car_make:"+car.getMake(), "car_color:"+car.getColor());
			List<Car> values = redisTemplate.opsForHash().multiGet(CAR_KEY, list1);
			
			System.out.println("=====================Car list1:"+list1);
			System.out.println("=====================Car values:"+values);
			
			return values;
		}
		
		
		@Override
		public void delete(String id)
		{
				this.redisTemplate.opsForHash().delete(CAR_KEY,id);
				
		}

}
