package com.huangjiabin;

import com.huangjiabin.After.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_After {
    @Test
    public void test(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService some=(SomeService) context.getBean("SomeServiceImpl5");
        some.doSome();
    }
}
