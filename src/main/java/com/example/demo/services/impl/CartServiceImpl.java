package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.services.CartService;

@Repository
public class CartServiceImpl implements  CartService
{
	
	@Autowired
	private RedisTemplate redisTemplate;
	 // private RedisTemplate<String, Object> redisTemplate;
	
	private static String CART_KEY = "vpa_Cart";
	private static String CART_KEY_LATEST = "vpa_latestCart";
		
		@SuppressWarnings("unchecked")
		@Override
		public void save(Cart cart)
		{
				redisTemplate.opsForHash().put(CART_KEY,cart.getUserId(),cart);
		 
		        redisTemplate.opsForList().leftPush(CART_KEY_LATEST, cart);
		        redisTemplate.opsForList().trim(CART_KEY_LATEST, 0, 1);

		}

		@Override
		public Cart find(String id)
		{
			Cart cart=(Cart)redisTemplate.opsForHash().get(CART_KEY, id);
			return (Cart)this.redisTemplate.opsForHash().get(CART_KEY, id);
		}

		@Override
		public Map<Object,Object> findAll()
		{
			
			Map<Object, Object> cartList = redisTemplate.opsForHash().entries(CART_KEY); 
			return redisTemplate.opsForHash().entries(CART_KEY);
		}
		
		public List<Cart> findLatestAll(){
	        List<Cart>  carts = redisTemplate.opsForList().range(CART_KEY_LATEST, 0, -1);
	        return carts;
	    }

	  
		@Override
		public void delete(String id)
		{
				this.redisTemplate.opsForHash().delete(CART_KEY,id);
				
		}

}
