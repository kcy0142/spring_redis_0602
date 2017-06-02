package com.example.demo.services;


import java.util.Map;

import com.example.demo.domain.Student;

public interface StudentService {

    void saveStudent(Student person);

    void updateStudent(Student student);

    Student findStudent(String id);

    Map<Object, Object> findAllStudents();

    void deleteStudent(String id);
}
