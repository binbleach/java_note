package com.huangjiabin.springboot.service;

import com.huangjiabin.springboot.domain.Student;
import com.huangjiabin.springboot.domain.swagger.BStudent;

public interface StudentService {
    Student queryStudentById(Integer id);

    int addStudentUser (BStudent student);
}
