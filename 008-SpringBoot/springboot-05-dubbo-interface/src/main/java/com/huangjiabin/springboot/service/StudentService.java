package com.huangjiabin.springboot.service;

import com.huangjiabin.springboot.model.Student;

public interface StudentService {
    Student queryStudentById(Integer id);

    int queryAllStudentCount();
}
