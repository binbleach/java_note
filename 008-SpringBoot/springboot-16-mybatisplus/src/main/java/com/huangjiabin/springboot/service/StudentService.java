package com.huangjiabin.springboot.service;

import com.huangjiabin.springboot.model.Student;

public interface StudentService {
    int addStudentUser (Student student);
    int updateStudentById (Student student);

}
