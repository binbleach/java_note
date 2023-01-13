package com.huangjiabin;

import com.huangjiabin.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class                                                                                                                                                          MyApp {
    //一般使用mybatis,这里没有封装util，也没有用到 SqlSession.mapper(接口.class)
    public static void main(String[] args) throws IOException {
        //1.mybatis 主配置文件,主配置文件名称，是从类路径的根下找的target/class
        String config = "mybatis.xml"; //mybatis不严格分大小写
        //2. 读取配置文件config
        InputStream in = Resources.getResourceAsStream(config);
        //3. 创建 SqlSessionFactory 对象, , 目的是获取 Sql Session
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //4. 获取 SqlSession,SqlSession 能执行 sql 语句
        SqlSession session = factory.openSession();


        //5. 执行 SqlSession 的 selectList()
        List<Student> studentList =
        session.selectList("com.huangjiabin.dao.StudentDao.selectStudents");//namespace+id
        //6. 循环输出查询结果
        studentList.forEach( student -> System.out.println(student));
        //7. 关闭 SqlSession ，释放资源
        session.close();

        /*
        * 三种处理bug的方法：
        * 1、重新编译,先点clean,再点compile。(确保文件存在和resources目录正确)
        * 2、重新构建项目,点idea导航栏上的Build->Build Project
        * 3、清除无效缓存,点File->Invalidate Caches/Restart...
        */
    }
}
