package com.huangjiabin.dao;

import com.huangjiabin.entity.Student;
import com.huangjiabin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentDaoImp implements StudentDao {
    @Override
    //普通的通过dao实现sql的调用，有迹可循，重复性多，可以用动态代理解决
    public List<Student> selectStudents() {
        SqlSession sqlsession = MyBatisUtils.getSqlSession();
        List<Student> students=sqlsession.selectList("com.huangjiabin.dao.StudentDao.selectStudents");
        sqlsession.close();
        return students;
    }

    @Override
    public int insertStudent(Student student) {
        SqlSession sqlsession = MyBatisUtils.getSqlSession(true);
        int num=sqlsession.insert("com.huangjiabin.dao.StudentDao.insertStudnet",student);
        sqlsession.close();
        return num;
    }
}
