package com.huangjiabin.dao;

import com.huangjiabin.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public interface StudentDao {
     //第一种传的是一个简单类型的参数，mapper文件中 #{任意字符} 获取
     Student selectStudentById(int id);
     //第二种传多个参，通过@Param("自定义名"),.xml文件引用也要是自定义名称
     List<Student> selectStudentByParams(@Param("name") String name,@Param("age")int age);
     //第三种传多个参，通过对象，insert就是这种
     List<Student> selectStudentByObjects(Student student);
     //第四种传多个参，按位置
     List<Student> selectStudentByIndexs(String name,int age);
     //第五种传多个参，通过Map
     List<Student> selectStudentByMaps(Map<String,Object> map);
}
