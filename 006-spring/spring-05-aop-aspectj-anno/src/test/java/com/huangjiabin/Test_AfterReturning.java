package com.huangjiabin;

import com.huangjiabin.AfterReturning.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_AfterReturning {
    @Test
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService some=(SomeService) context.getBean("SomeServiceImpl2");
        System.out.println(some.doSome("ad", 2));
    }
}
