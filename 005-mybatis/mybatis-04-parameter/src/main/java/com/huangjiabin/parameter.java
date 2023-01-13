package com.huangjiabin;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/*
* java向mapper传参
*/
public class parameter {
    public static void main(String[] args) {
        SqlSession sqlsession = MyBatisUtils.getSqlSession();
        StudentDao dao=sqlsession.getMapper(StudentDao.class);
        /*01 一个值*/
        Student student=dao.selectStudentById(4);
        System.out.println(student);
        /*02 Param*/
    dao.selectStudentByParams("黄家宾",30).forEach(stu-> System.out.println(stu));
        /*03 Object*/
        dao.selectStudentByObjects(new Student(3,"帅哥",
                "",null)).forEach(stu-> System.out.println(stu));
        /*04 index*/
        dao.selectStudentByIndexs("刘备",32).forEach(stu-> System.out.println(stu));
        /*05 Map*/
        Map map = new HashMap();
        map.put("key1","丑逼");
        map.put("key2",25);
        dao.selectStudentByMaps(map).forEach(stu-> System.out.println(stu));

    }
}
