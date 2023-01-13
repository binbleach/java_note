package com.huangjiabin.dao;

import com.huangjiabin.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentDao {
     List<Student> selectStudentIf(Student stu);

     List<Student> selectStudentWhere(Student stu);

     List<Student> selectStudentForeach(List<Student> list1);
}
