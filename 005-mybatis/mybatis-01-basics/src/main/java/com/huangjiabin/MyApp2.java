package com.huangjiabin;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class MyApp2 {
    /*用了封装，和 mapper*/
    public static void main(String[] args) throws IOException {
        SqlSession sqlSession = MyBatisUtils.getSqlSession(false);

        Student student = new Student(10,"丑逼","chou@qq.com",25);
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        int num=dao.insertStudents(student);
        System.out.println(num);
    }
}
