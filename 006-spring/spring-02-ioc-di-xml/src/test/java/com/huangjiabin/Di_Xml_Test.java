package com.huangjiabin;

import com.huangjiabin.di_xml_set.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class Di_Xml_Test {
    @Test
    public void test1(){
        //设置注入，实际上调用的时set方法
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student)context.getBean("Student");
        System.out.println(student);
    }
    @Test
    public void test2(){
        //引用类型的set注入
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student)context.getBean("Student");
        System.out.println(student);
    }

    @Test
    public void test3(){
        //构造器注入
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        com.huangjiabin.di_xml_constructor.Student student = (com.huangjiabin.di_xml_constructor.Student) context.getBean("Student2");
        System.out.println(student);
        //构造器下标注入
        student = (com.huangjiabin.di_xml_constructor.Student) context.getBean("Student3");
        System.out.println(student);
        //构造器调用其他类
        File file =(File)context.getBean("File");
        System.out.println(file.getName());
    }

    @Test
    public void test4(){
        //自动注入引用类型byName
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        com.huangjiabin.di_xml_autowire.Student student = (com.huangjiabin.di_xml_autowire.Student) context.getBean("Student4");
        System.out.println(student);

        /*byType*/
        student = (com.huangjiabin.di_xml_autowire.Student) context.getBean("Student5");
        System.out.println(student);
    }
}
