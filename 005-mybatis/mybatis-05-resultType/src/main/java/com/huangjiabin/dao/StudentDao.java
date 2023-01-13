package com.huangjiabin.dao;

import com.huangjiabin.entity.MyStudent;
import com.huangjiabin.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentDao {
     //第一种返回的是一个对象
     List<Student> selectStudents(int id);

     //第二种返回的是一个数
     int selectStudentCount();

     //第三种返回的是Map,返回的是Map的话只能返回一条数据
     Map selectStudentMap(Map<String,String> map);

     //第四种resultMap
     MyStudent selectMyStudent(int id);

     //第一种模糊查询
     Student selectStudentLike1(String name);

     //第二种模糊查询
     Student selectStudentLike2(String naem);

}
