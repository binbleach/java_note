package com.huangjiabin;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.MyStudent;
import com.huangjiabin.entity.Student;
import com.huangjiabin.utils.MyBatisUtils;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestResultType {
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        //第一种对象类型
        List<Student> students=dao.selectStudents(1);
        for(Student s :students){
            System.out.println(s);
        }
        //第二种简单类型
        System.out.println(dao.selectStudentCount());

        //第三种map类型
        Map map = new HashMap();
        map.put("key1","黄家宾");
        Map map1=dao.selectStudentMap(map);
        System.out.println(map1.values());

        //第四种resultMap类型
        MyStudent my=dao.selectMyStudent(7);
        System.out.println(my);

        //两种模糊查询的方式
        /* 01 直接*/
        String sql = "%刘%";
        System.out.println(dao.selectStudentLike1(sql));

        /*02 不直接*/
        System.out.println(dao.selectStudentLike2("明"));

    }
}
