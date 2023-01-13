package com.huangjiabin;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotation {
    @Test
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student=(Student) context.getBean("MyStudent");
        System.out.println(student);
        Subject subject=(Subject) context.getBean("subject");
        System.out.println(subject);
    }
}
