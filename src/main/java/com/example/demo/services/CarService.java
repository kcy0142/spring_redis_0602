package com.example.demo.services;

import java.util.List;
import java.util.Map;

import com.example.demo.domain.Car;
import com.example.demo.domain.Cart;

public interface CarService {
	
	public int save(Car car);
	
	public Car find(String id);
	
	public Map<Object, Object> findAll();
	
	public Map<Object, Object> findAll1();
	
	public void	delete(String id);
	
	public List<Car> findSearchAll(Car car);
	
}
