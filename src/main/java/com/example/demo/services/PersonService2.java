package com.example.demo.services;

import java.util.List;
import java.util.Map;

import com.example.demo.domain.Person;

public interface PersonService2 {
	
	public void save(Person person);
	
	public Person find(String org,String id);
	
	
	public Map<Object, Object> findAll(Person person);
	
	public void	delete(String org,String id);
	
}
