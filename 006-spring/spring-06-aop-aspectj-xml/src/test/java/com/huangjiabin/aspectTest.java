package com.huangjiabin;

import static org.junit.Assert.assertTrue;

import com.huangjiabin.service.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class aspectTest
{
    @Test
    public void before()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        SomeService someService = (SomeService) context.getBean("someServiceImpl");
        someService.doSome();
    }
}
