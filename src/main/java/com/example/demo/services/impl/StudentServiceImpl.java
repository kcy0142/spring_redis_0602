package com.example.demo.services.impl;

import java.util.Map;

import com.example.demo.domain.Student;
import com.example.demo.services.StudentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

public class StudentServiceImpl implements StudentService {

	private static final String KEY = "Student";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public StudentServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void saveStudent(final Student student) {
        hashOperations.put(KEY, student.getId(), student);
    }

    public void updateStudent(final Student student) {
        hashOperations.put(KEY, student.getId(), student);
    }

    public Student findStudent(final String id) {
        return (Student) hashOperations.get(KEY, id);
    }

    public Map<Object, Object> findAllStudents() {
        return hashOperations.entries(KEY);
    }

    public void deleteStudent(final String id) {
        hashOperations.delete(KEY, id);
    }


}
