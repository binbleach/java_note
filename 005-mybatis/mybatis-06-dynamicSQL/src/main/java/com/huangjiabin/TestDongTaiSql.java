package com.huangjiabin;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class TestDongTaiSql {
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        Student student=new Student(2, "", "", 25);

        dao.selectStudentIf(student).forEach(stu-> System.out.println(stu));

        dao.selectStudentWhere(student).forEach(stu-> System.out.println(stu));

        List<Student> list = new ArrayList<>();
        Student student1=new Student(3, "", "", 25);
        Student student2=new Student(4, "", "", 25);
        list.add(student);
        list.add(student1);
        list.add(student2);
        dao.selectStudentForeach(list).forEach(stu-> System.out.println(stu));
    }
}
