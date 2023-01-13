package com.huangjiabin.dao;

import com.huangjiabin.entity.Student;

import java.util.List;

public interface StudentDao {
     List<Student> selectStudents();
     int insertStudent(Student student);
}
