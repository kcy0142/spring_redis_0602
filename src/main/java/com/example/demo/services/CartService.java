package com.example.demo.services;

import java.util.List;
import java.util.Map;

import com.example.demo.domain.Cart;

public interface CartService {
	
	public void save(Cart cart);
	
	public Cart find(String id);
	
	public Map<Object, Object> findAll();
	
	public void	delete(String id);
	
	public List<Cart> findLatestAll();
	
}
