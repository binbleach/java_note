package com.huangjiabin;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
/*
*本身dao层写实现类就是不方便所以动态代理
* namespace:要求与dao接口全限定名一致就是为了动态代理
* 动态代理，直接就可以获取接口对象
* 动态代里必须是接口，可以实现功能增强
*/
public class Proxy {
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession(false);
        StudentDao dao=sqlSession.getMapper(StudentDao.class); //传入接口类，直接获取接口对象
        System.out.println(dao.getClass().getName()); //获得的是：com.sun.proxy.$Proxy4 动态代理
        List students=dao.selectStudents();
        students.forEach(stu -> System.out.println(stu));
        Student student = new Student(20,"孔明","kong@qq.com",32);
        int num=dao.insertStudent(student);
        /*sqlSession.commit();*/
        System.out.println(num);
    }
}
