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

import com.example.demo.domain.Person;
import com.example.demo.services.PersonService2;

@Repository
public class PersonServiceImpl2 implements  PersonService2
{
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	private static String PERSON_KEY = "vpa_Person";
	
	private static String PERSON_KEY_TEST = "vpa_Person_test";
	
	private static String PERSON_KEY_TEST_PIPE = "vpa_Person_test_pipe";
	
	private static String PERSON_KEY_NO_PIPE = "vpa_Person_test_no_pipe";
	
	private static String PERSON_KEY_TEST_MULTI = "vpa_Person_test_multi";
		
		@SuppressWarnings("unchecked")
		@Override
		public void save(Person person)
		{
				redisTemplate.opsForHash().put(PERSON_KEY+":"+person.getOrganization(),person.getId(),person);
				
				
				
				long start = System.currentTimeMillis();
				for(int a=0;a<30;a++){
					for(int b=0;b<1000;b++){
						
						 Person person1 = new Person(); 
						 person1.setName("test_man"+a+"_"+b);
						 person1.setAge(23);
						 person1.setGender("man");
						 person1.setId("test"+a+"_"+b);
						 person1.setOrganization("lgmedical");
						 
						 redisTemplate.opsForHash().put(PERSON_KEY_TEST+":"+person1.getOrganization(),person1.getId(),person1);
					}
				}
				long end = System.currentTimeMillis();
				System.out.println("Total Simple SET: " + ((end - start)/1000.0) + " seconds");
				
				
				start = System.currentTimeMillis();
				for(int a=0;a<30;a++){
					List<Person> entries = new ArrayList<>();
					for(int b=0;b<1000;b++){
						
						 Person person1 = new Person(); 
						 person1.setName("test_man"+a+"_"+b);
						 person1.setAge(23);
						 person1.setGender("man");
						 person1.setId("test"+a+"_"+b);
						 person1.setOrganization("lgmedical");
						 entries.add(person1);
					}
					saveMultiList(entries);
				}
				end = System.currentTimeMillis();
				System.out.println("Total Pipelined SET(saveMultiList): " + ((end - start)/1000.0) + " seconds");
				
				
				start = System.currentTimeMillis();
				for(int a=0;a<30;a++){
					List<Person> entries = new ArrayList<>();
					for(int b=0;b<1000;b++){
						
						 Person person1 = new Person(); 
						 person1.setName("test_man"+a+"_"+b);
						 person1.setAge(23);
						 person1.setGender("man");
						 person1.setId("test"+a+"_"+b);
						 person1.setOrganization("lgmedical");
						 entries.add(person1);
					}
					txUsedPool(entries);
				}
				end = System.currentTimeMillis();
				System.out.println("Total multi SET(txUsedPool): " + ((end - start)/1000.0) + " seconds");
				
				start = System.currentTimeMillis();
				for(int a=0;a<30;a++){
					List<Person> entries = new ArrayList<>();
					for(int b=0;b<1000;b++){
						
						 Person person1 = new Person(); 
						 person1.setName("test_man"+a+"_"+b);
						 person1.setAge(23);
						 person1.setGender("man");
						 person1.setId("test"+a+"_"+b);
						 person1.setOrganization("lgmedical");
						 entries.add(person1);
					}
					saveMultiListForSimple(entries);
				}
				end = System.currentTimeMillis();
				System.out.println("Total saveMultiListForSimple: " + ((end - start)/1000.0) + " seconds");
				/*
				SetOperations<String, String> ops = redisTemplate.opsForSet();
				long start = System.currentTimeMillis();
				for(int i=0; i < 100000; i++){
					Long rslt = ops.add("key:"+i, ""+i);
				}
				long end = System.currentTimeMillis();
				System.out.println("Simple SET: " + ((end - start)/1000.0) + " seconds"); 

				////////////////////////////////////////////////////////////////////////////////////////////////////////////
				start = System.currentTimeMillis();
				redisTemplate.execute(new RedisCallback<Object>(){
					@Override
					public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
					connection.openPipeline(); 
					for(int i=0; i < 100000; i++){
						connection.set(("key."+i).getBytes(), (""+i).getBytes());
					}
					List<Object> rslts = connection.closePipeline();
					//System.out.println(">>>>>>>>>>> " + rslts.get(0)); 
					return null;
					}
				});
				end = System.currentTimeMillis();
				System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");
				//Simple SET: 13.463 seconds
				//Pipelined SET: 0.589 seconds
				*/
				
		}

		
		@SuppressWarnings("unchecked")
		public void saveMultiList(List personList){ 
			//long start = System.currentTimeMillis();
			int start,end=0;
			HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
	 		redisTemplate.executePipelined(new RedisCallback<Object>(){ 

	 			public Object doInRedis(RedisConnection connection) 
	 					throws DataAccessException { 
	 				
	 				for(int a=0;a<personList.size();a++){
	 					
	 					Person p=(Person) personList.get(a);
		 	            hashOps.put(PERSON_KEY_TEST_PIPE+":"+p.getOrganization(),p.getId(),p);
	 				}
	 				
	 				
	 				return null; 
	 			} 
	 			 
	 		}); 
	 		//long end = System.currentTimeMillis();
	 		//System.out.println("Pipelined SET(saveMultiList): " + ((end - start)/1000.0) + " seconds");
	 		
		}
		
		
		@SuppressWarnings("unchecked")
		public void saveMultiListForSimple(List personList){ 
			
			//redisTemplate.opsForHash().put(PERSON_KEY+":"+person.getOrganization(),person.getId(),person);
			long start = System.currentTimeMillis();
			for(int a=0;a<personList.size();a++){
					Person p=(Person) personList.get(a);
					redisTemplate.opsForHash().put(PERSON_KEY_NO_PIPE+":"+p.getOrganization(),p.getId(),p);
				}
	 		long end = System.currentTimeMillis();
	 		//System.out.println("Pipelined SET(saveMultiListForSimple): " + ((end - start)/1000.0) + " seconds");
	 		
		}
		
		
	 	 public void txUsedPool(List personList){ 
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
	 	       //System.out.println("=============idx:"+personInfo.getIdx());
	 	       	redisTemplate.execute(sessionCallback); 
	 	       
	 	   }
	 	 
	 	 public void txUsedPoolSample(){  
	 	        SessionCallback<Person> sessionCallback = new SessionCallback<Person>() {  
	 	            @Override  
	 	           public Person execute(RedisOperations operations) throws DataAccessException {  
	 	               operations.multi();  
	 	               Person person = new Person(); 
	 	               person.setName("test_man");
	 	               person.setAge(23);
	 	               person.setGender("man");
	 	               person.setId("test");
	 	               person.setOrganization("lgmedical");
	 	               String key = "user:dddff"; 
	 	               String key1 = "user:" + person.getId(); 
	 	               BoundValueOperations<String, Person> oper = operations.boundValueOps(key);  
	 	               // HashOperations<K,HK,HV>
	 	               oper.set(person);  
	 	               //oper.expire(60, TimeUnit.MINUTES);  
	 	               //redisTemplate.opsForHash().put(PERSON_KEY+":"+person.getOrganization(),person.getId(),person);
	 	               HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
	 	               hashOps.put(PERSON_KEY_TEST+":"+person.getOrganization(),person.getId(),person);
	 	               operations.exec();  
	 	               return person;  
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
