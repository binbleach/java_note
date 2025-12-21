package com.huangjiabin;

import com.huangjiabin.Annotation.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_Annotation {
    @Test
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService some=(SomeService) context.getBean("SomeServiceImpl6");
        some.doSome("ac",2);
    }
}
