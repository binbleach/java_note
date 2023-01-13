package com.huangjiabin;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.service.StudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_Mybatis_Spring {

    @Test
    public void testDao(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentDao dao=(StudentDao) context.getBean("studentDao");
        dao.selectStudents().forEach(student -> System.out.println(student));
    }

    @Test
    public void testObject(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        String names[]=context.getBeanDefinitionNames();
        for(String name:names){
            System.out.println("=====================");
            System.out.println("对象名："+name+"对象类型"+context.getBean(name).getClass().getName());
        }
    }
    @Test
    public void testService(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentService service=(StudentService) context.getBean("studentService");
        Student s = new Student(8,"王力","wang@qq.com",30);
        System.out.println("=====================");
        System.out.println(service.addStudent(s)); //spring整合mybatis时事物自动提交无需执行sqlSession.commit()
    }
}
