package com.example.demo.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo.domain.Login;


public interface LoginService {
	
	public Set<String>  retrieveDb();
	
	public void save(Login login);
	
	public String findAll(Login login);
}
