package com.huangjiabin;

import com.huangjiabin.dao.StudentDaoImp;
import com.huangjiabin.entity.Student;

import java.util.List;

public class MyBatisTest {
    public static void main(String[] args) {
        StudentDaoImp dao = new StudentDaoImp();
        List list=dao.selectStudents();
        list.forEach(stu -> System.out.println(stu));
        /*Student student = new Student(7,"刘备","liu@qq.com",40);
        int num=dao.insertStudent(student);
        System.out.println(num);*/
    }
}
