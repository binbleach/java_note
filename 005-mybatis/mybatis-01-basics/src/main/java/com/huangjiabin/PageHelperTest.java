package com.huangjiabin;

import com.github.pagehelper.PageHelper;
import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class PageHelperTest {
    /*分页查询
     *1、加依赖，加插件（在mybatis主配置）
    * */
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        PageHelper.startPage(2,3);//三行一页的第一页。
        List<Student> list=dao.pageHelper();
        list.forEach(stu-> System.out.println(stu));
    }
}
