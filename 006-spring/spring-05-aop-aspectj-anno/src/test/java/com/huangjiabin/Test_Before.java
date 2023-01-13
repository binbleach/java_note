package com.huangjiabin;

import com.huangjiabin.Before.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_Before {
    @Test
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService some=(SomeService) context.getBean("SomeServiceImpl");
        some.doSome("ac",2);
    }
}
