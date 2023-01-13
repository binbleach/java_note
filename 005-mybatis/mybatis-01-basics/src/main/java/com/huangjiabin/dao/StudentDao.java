package com.huangjiabin.dao;

import com.huangjiabin.entity.Student;

import java.util.List;

/*接口来操作StudentDao*/
public interface StudentDao {
    //01查询所有数据，返回的是集合，每一条数据都是一个对象
    //List<Student> selectStudents(); //方法对应的是sql的执行，写在sql映射中xml文件中，一个表一个文件
    int insertStudents(Student student);
    List<Student> pageHelper();
}
