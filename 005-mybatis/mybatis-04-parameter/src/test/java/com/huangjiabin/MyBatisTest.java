package com.huangjiabin;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MyBatisTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        /*List students=dao.selectStudents();
        students.forEach(stu -> System.out.println(stu));*/
    }
}
