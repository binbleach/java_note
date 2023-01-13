package com.huangjiabin;

import com.huangjiabin.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyAppTest {
    //获取流，流build进SqlSessionFactoryBuilder,得SqlSessionFactory,openSession得到SqlSession
    public static void main(String[] args) throws IOException {
        //mybatis.xml 主配置文件,主配置文件名称，是从类路径的根下找的target/class
        //1.获取输入流，Resources类，用来读取配置文件config
        InputStream in = Resources.getResourceAsStream("mybatis.xml");
        //2.获取SqlSessionFactoryBuilder对象, , 目的是获取 SqlSessionFactory
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3.获取SqlSessionFactory(Factory工厂),重量级对象本身是个接口，创建比较耗时，一个项目一个就够了
        SqlSessionFactory factory = builder.build(in);
        //4. 获取 SqlSession,能执行 sql 语句，带参true可以自动提交，也是接口，非线程安全，得close.
        SqlSession sqlSession = factory.openSession();
        Student student = new Student(4,"帅哥","shuai@qq.com",23);
        //5. 执行 SqlSession 的 insert()
        int num =
                sqlSession.insert("com.huangjiabin.dao.StudentDao.insertStudents",student);//namespace+id
        //6.mybatis不会自动提交，得设置，
        /*sqlSession.commit();*/
        //7.输出
        System.out.println(num);
        //8. 关闭 SqlSession ，释放资源
        sqlSession.close();
    }
}
