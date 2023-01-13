package com.huangjiabin.com.service;

import com.huangjiabin.com.entity.Student;

import java.util.List;

public interface StudentService {
    int addStudent(Student student);
    List<Student> selectStudent();
}
