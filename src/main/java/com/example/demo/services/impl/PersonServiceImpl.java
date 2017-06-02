package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Car;
import com.example.demo.domain.Person;
import com.example.demo.domain.Product;
import com.example.demo.services.PersonService;

@Repository
public class PersonServiceImpl implements  PersonService
{
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	private static String PERSON_KEY = "vpa_Person";
	
	private static String PERSON_KEY_TEST = "vpa_Person_test";
	
	private static String PERSON_KEY_TEST_MULTI = "vpa_Person_test_multi";
	
	private static String PERSON_KEY_NO_MULTI = "vpa_Person_test_no_multi";
	
	
		
		@SuppressWarnings("unchecked")
		@Override
		public void save(Person person)
		{
				redisTemplate.opsForHash().put(PERSON_KEY+":"+person.getOrganization(),person.getId(),person);
				
				
				
//				long start = System.currentTimeMillis();
//				for(int a=0;a<3;a++){
//					for(int b=0;b<100000;b++){
//						
//						 Person person1 = new Person(); 
//						 person1.setName("test_man"+a+"_"+b);
//						 person1.setAge(23);
//						 person1.setGender("man");
//						 person1.setId("test"+a+"_"+b);
//						 person1.setOrganization("lgcns");
//						 
//						 redisTemplate.opsForHash().put(PERSON_KEY_TEST+":"+person1.getOrganization(),person1.getId(),person1);
//					}
//				}
//				long end = System.currentTimeMillis();
//				System.out.println("Total Simple SET: " + ((end - start)/1000.0) + " seconds");
//				
//				
//				start = System.currentTimeMillis();
//				for(int a=0;a<3;a++){
//					List<Person> entries = new ArrayList<>();
//					for(int b=0;b<100000;b++){
//						
//						 Person person1 = new Person(); 
//						 person1.setName("test_man"+a+"_"+b);
//						 person1.setAge(23);
//						 person1.setGender("man");
//						 person1.setId("test"+a+"_"+b);
//						 person1.setOrganization("lgcns");
//						 entries.add(person1);
//					}
//					saveMultiList(entries);
//				}
//				end = System.currentTimeMillis();
//				System.out.println("Total saveMultiList: " + ((end - start)/1000.0) + " seconds");
//				
//				start = System.currentTimeMillis();
//				for(int a=0;a<3;a++){
//					List<Person> entries = new ArrayList<>();
//					for(int b=0;b<100000;b++){
//						
//						 Person person1 = new Person(); 
//						 person1.setName("test_man"+a+"_"+b);
//						 person1.setAge(23);
//						 person1.setGender("man");
//						 person1.setId("test"+a+"_"+b);
//						 person1.setOrganization("lgcns");
//						 entries.add(person1);
//					}
//					saveMultiListForSimple(entries);
//				}
//				end = System.currentTimeMillis();
//				System.out.println("Total saveMultiListForSimple: " + ((end - start)/1000.0) + " seconds");
				
		}
		
		@SuppressWarnings("unchecked")
		public void saveMultiListForSimple(List personList){ 
			for(int a=0;a<personList.size();a++){
					Person p=(Person) personList.get(a);
					redisTemplate.opsForHash().put(PERSON_KEY_NO_MULTI+":"+p.getOrganization(),p.getId(),p);
				}
		}
		
		
	 	 public void saveMultiList(List personList){ 
	 		 	HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
	 	        SessionCallback<Person> sessionCallback = new SessionCallback<Person>() {  
	 	        	
	 	            @Override  
	 	           public Person execute(RedisOperations operations) throws DataAccessException {  
	 	               operations.multi();  
	 	             
		 	          	for(int a=0;a<personList.size();a++){
		 					Person p=(Person) personList.get(a);
			 	            hashOps.put(PERSON_KEY_TEST_MULTI+":"+p.getOrganization(),p.getId(),p);
		 				}
	 	               operations.exec();  
	 	               return null;  
	 	           }  
	 	       };  
	 	       	redisTemplate.execute(sessionCallback); 
	 	       
	 	   }
	 	 
		@Override
		public Person find(String org,String id)
		{
				return (Person)this.redisTemplate.opsForHash().get(PERSON_KEY+":"+org, id);
		}

				
		@Override
		public Map<Object,Object> findAll(Person person)
		{
			return redisTemplate.opsForHash().entries(person.getOrganization()==null?"vpa_Person:lgcns":"vpa_Person:"+person.getOrganization());
		}		
	  
		@Override
		public void delete(String org,String id)
		{
				this.redisTemplate.opsForHash().delete(PERSON_KEY+":"+org,id);
				
		}

}
