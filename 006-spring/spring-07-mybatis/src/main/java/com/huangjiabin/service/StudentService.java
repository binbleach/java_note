package com.huangjiabin.service;

import com.huangjiabin.entity.Student;

import java.util.List;

public interface StudentService {
    int addStudent (Student s);
    List<Student> queryStudents();
}
