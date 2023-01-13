package com.huangjiabin;

import com.huangjiabin.Around.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test_Around {
    @Test
    public void test(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService some=(SomeService) context.getBean("SomeServiceImpl3");
        some.doSome("黄家宾",23);
    }

}
